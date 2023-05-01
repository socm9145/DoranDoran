package com.purple.core.database.dao

import androidx.room.*
import com.purple.core.database.entity.RoomEntity
import com.purple.core.database.model.RoomWithMembers
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoom(room: RoomEntity)

    @Transaction
    @Query("SELECT * FROM rooms")
    fun getRoomsWithMembers(): Flow<List<RoomWithMembers>>
}
