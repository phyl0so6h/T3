package com.sahak7an.t3.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sahak7an.t3.core.ui.UiCourse
import com.sahak7an.t3.domain.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesViewModel(
    private val repository: CoursesRepository
) : ViewModel() {
    private val _favorites = MutableStateFlow<List<UiCourse>>(emptyList())
    val favorites: StateFlow<List<UiCourse>> = _favorites.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                repository.observeCached(),
                repository.observeFavorites(),
                repository.observeHidden()
            ) { cached, favIds, hidden ->
                val union = cached.filter { course ->
                    val serverLiked = course.hasLike && !hidden.contains(course.id)
                    val locallyLiked = favIds.contains(course.id)
                    serverLiked || locallyLiked
                }
                union.map {
                    UiCourse(
                        id = it.id,
                        title = it.title,
                        text = it.text,
                        price = it.price,
                        rate = it.rate,
                        startDate = it.startDate,
                        publishDate = it.publishDate,
                        isFavorite = true
                    )
                }
            }.collect { list -> _favorites.value = list }
        }
    }
}


