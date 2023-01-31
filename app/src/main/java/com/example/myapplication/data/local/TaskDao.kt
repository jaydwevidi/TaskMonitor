package com.example.myapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.Task


@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM Task ORDER BY id DESC")
    fun readAllData(): LiveData<List<Task>>

    @Query("DELETE FROM Task ")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTaskList(task: List<Task>)
}