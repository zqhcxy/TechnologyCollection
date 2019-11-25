package com.example.technologycollection.db

import android.database.Cursor
import androidx.room.*
import io.reactivex.Flowable

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllToList(): Flowable <List<User>>

    @Query("SELECT * FROM user")
    fun getALLToCursor():Cursor

    @Query("SELECT * FROM user WHERE uid in (:userIds)")
    fun loadAllByIds(userIds:IntArray):List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name like :last LIMIT 1")
    fun findByName(first:String,last:String):List<User>

    @Query("SELECT * FROM user WHERE age in (:ages)")
    fun findByAges(ages:IntArray):List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users:User)//可变数量参数vararg 就是数组

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user:User)//可变数量参数vararg 就是数组

    @Delete
    fun delete(user:User)

    @Update
    fun update(user:User)


}