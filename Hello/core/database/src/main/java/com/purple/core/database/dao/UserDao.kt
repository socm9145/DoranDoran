package com.purple.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.purple.core.database.entity.MemberEntity
import com.purple.core.database.entity.MemberRoomEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMember(member: MemberEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMemberRoom(memberRoomEntity: MemberRoomEntity)
}
