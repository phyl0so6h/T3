package com.sahak7an.t3.core.network

import retrofit2.http.GET
import retrofit2.http.Url

data class CoursesResponse(
    val courses: List<CourseDto>
)

data class CourseDto(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
)

interface CoursesApi {
    @GET
    suspend fun getCourses(
        @Url url: String = "https://drive.usercontent.google.com/u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download"
    ): CoursesResponse
}


