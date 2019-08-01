package com.template.app.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM USER_DB")
    fun getUsers(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAllUser(users: List<UserEntity>)

    @Delete
    fun deleteUser(user: UserEntity)

    @Query("DELETE FROM USER_DB")
    fun deleteAll()
}