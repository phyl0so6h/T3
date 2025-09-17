package com.sahak7an.t3.domain

import com.sahak7an.t3.core.model.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    suspend fun fetchCourses(): List<Course>
    fun observeFavorites(): Flow<Set<Int>>
    suspend fun setFavorite(courseId: Int, isFavorite: Boolean)
    fun observeCached(): Flow<List<Course>>
    fun observeHidden(): Flow<Set<Int>>
    suspend fun setHidden(courseId: Int, hidden: Boolean)
}


