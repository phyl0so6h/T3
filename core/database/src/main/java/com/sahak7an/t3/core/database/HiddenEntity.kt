package com.sahak7an.t3.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hidden_likes")
data class HiddenEntity(
    @PrimaryKey val courseId: Int
)


