package com.cha1se.roomexampleapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Query("SELECT * FROM Task")
    fun getTasks(): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("SELECT * FROM Task WHERE id == :id")
    fun getTaskById(id: Int): List<Task>
}