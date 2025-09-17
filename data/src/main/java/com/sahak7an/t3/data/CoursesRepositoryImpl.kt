package com.sahak7an.t3.data

import com.sahak7an.t3.core.database.FavoriteEntity
import com.sahak7an.t3.core.database.CoursesDao
import com.sahak7an.t3.core.database.CourseEntity
import com.sahak7an.t3.core.database.FavoritesDao
import com.sahak7an.t3.core.database.HiddenDao
import com.sahak7an.t3.core.database.HiddenEntity
import com.sahak7an.t3.core.model.Course
import com.sahak7an.t3.core.network.CoursesApi
import com.sahak7an.t3.domain.CoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.map

class CoursesRepositoryImpl(
    private val api: CoursesApi,
    private val favoritesDao: FavoritesDao,
    private val coursesDao: CoursesDao,
    private val hiddenDao: HiddenDao
) : CoursesRepository {

    override suspend fun fetchCourses(): List<Course> {
        val response = api.getCourses()
        val mapped = response.courses.map {
            Course(
                id = it.id,
                title = it.title,
                text = it.text,
                price = it.price,
                rate = it.rate,
                startDate = it.startDate,
                hasLike = it.hasLike,
                publishDate = it.publishDate
            )
        }
        // Cache locally
        coursesDao.upsertAll(mapped.map { c ->
            CourseEntity(
                id = c.id,
                title = c.title,
                text = c.text,
                price = c.price,
                rate = c.rate,
                startDate = c.startDate,
                hasLike = c.hasLike,
                publishDate = c.publishDate
            )
        })
        return mapped
    }

    override fun observeFavorites(): Flow<Set<Int>> =
        favoritesDao.observeAll().map { list -> list.map { it.courseId }.toSet() }

    override suspend fun setFavorite(courseId: Int, isFavorite: Boolean) {
        if (isFavorite) favoritesDao.insert(FavoriteEntity(courseId))
        else favoritesDao.delete(FavoriteEntity(courseId))
    }

    override fun observeCached(): Flow<List<Course>> =
        coursesDao.observeAll().map { list ->
            list.map {
                Course(
                    id = it.id,
                    title = it.title,
                    text = it.text,
                    price = it.price,
                    rate = it.rate,
                    startDate = it.startDate,
                    hasLike = it.hasLike,
                    publishDate = it.publishDate
                )
            }
        }

    override fun observeHidden(): Flow<Set<Int>> =
        hiddenDao.observeAll().map { list -> list.map { it.courseId }.toSet() }

    override suspend fun setHidden(courseId: Int, hidden: Boolean) {
        if (hidden) hiddenDao.insert(HiddenEntity(courseId)) else hiddenDao.delete(HiddenEntity(courseId))
    }
}


