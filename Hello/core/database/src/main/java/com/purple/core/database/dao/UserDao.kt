package com.purple.core.database.dao

import androidx.room.*
import com.purple.core.database.entity.MemberEntity
import com.purple.core.database.entity.MemberRoomEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user_room_cross WHERE roomId = :roomId AND userId = :userId")
    fun getUserInRoom(userId: Long, roomId: Long): MemberRoomEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMember(member: MemberEntity)

    @Update
    fun updateMember(member: MemberEntity): Int

    @Transaction
    fun upsertMember(member: MemberEntity) {
        val updatedRows = updateMember(member)
        if (updatedRows == 0) {
            insertMember(member)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMemberRoom(memberRoomEntity: MemberRoomEntity)

    @Update
    fun updateMemberRoom(memberRoomEntity: MemberRoomEntity): Int

    @Transaction
    fun upsertMemberRoom(memberRoomEntity: MemberRoomEntity) {
        val updatedRows = updateMemberRoom(memberRoomEntity)
        if (updatedRows == 0) {
            insertMemberRoom(memberRoomEntity)
        }
    }
}
