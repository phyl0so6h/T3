package com.sahak7an.t3.data

import com.sahak7an.t3.domain.CoursesRepository
import org.koin.dsl.module

val dataModule = module {
    single<CoursesRepository> { CoursesRepositoryImpl(get(), get(), get(), get()) }
}


