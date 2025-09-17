package com.sahak7an.t3.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sahak7an.t3.core.ui.UiCourse
import com.sahak7an.t3.domain.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: CoursesRepository
) : ViewModel() {

    private val _courses = MutableStateFlow<List<UiCourse>>(emptyList())
    val courses: StateFlow<List<UiCourse>> = _courses.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var rawCourses: List<UiCourse> = emptyList()

    init {
        observeFavorites()
        // First show cached courses
        viewModelScope.launch {
            repository.observeCached().collect { cached ->
                if (cached.isNotEmpty() && _courses.value.isEmpty()) {
                    rawCourses = cached.map {
                        UiCourse(
                            id = it.id,
                            title = it.title,
                            text = it.text,
                            price = it.price,
                            rate = it.rate,
                            startDate = it.startDate,
                            publishDate = it.publishDate,
                            isFavorite = it.hasLike
                        )
                    }
                    _courses.value = rawCourses
                }
            }
        }
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            val base: List<UiCourse> = try {
                val network = withContext(Dispatchers.IO) { repository.fetchCourses() }
                network.map {
                    UiCourse(
                        id = it.id,
                        title = it.title,
                        text = it.text,
                        price = it.price,
                        rate = it.rate,
                        startDate = it.startDate,
                        publishDate = it.publishDate,
                        isFavorite = it.hasLike
                    )
                }
            } catch (e: Exception) {
                _courses.value = emptyList()
                _error.value = "Can't load courses"
                _loading.value = false
                return@launch
            }
            rawCourses = base
            _courses.value = base
            _loading.value = false
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            combine(
                repository.observeFavorites(),
                repository.observeHidden(),
                _courses
            ) { favs, hidden, list ->
                val baseById = rawCourses.associateBy { it.id }
                list.map { c ->
                    val serverLiked = baseById[c.id]?.isFavorite == true
                    val locallyLiked = favs.contains(c.id)
                    val isHidden = hidden.contains(c.id)
                    c.copy(isFavorite = (serverLiked && !isHidden) || locallyLiked)
                }
            }.collect { merged -> _courses.value = merged }
        }
    }

    fun sortByPublishDateDesc() {
        _courses.value = _courses.value.sortedByDescending { it.publishDate }
    }

    fun toggleFavorite(course: UiCourse) {
        viewModelScope.launch {
            val wantsFavorite = !course.isFavorite
            if (baseHasLike(course.id)) {
                // If server-liked: hide when user unfavorites, and don't store duplicate favorite
                repository.setHidden(course.id, hidden = !wantsFavorite)
                repository.setFavorite(course.id, wantsFavorite)
            } else {
                repository.setFavorite(course.id, wantsFavorite)
            }
        }
    }

    private fun baseHasLike(id: Int): Boolean = rawCourses.any { it.id == id && it.isFavorite }
}


