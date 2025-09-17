package com.sahak7an.t3.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HiddenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: HiddenEntity)

    @Delete
    suspend fun delete(entity: HiddenEntity)

    @Query("SELECT * FROM hidden_likes")
    fun observeAll(): Flow<List<HiddenEntity>>
}


