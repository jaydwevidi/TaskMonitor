package com.example.myapplication.data

import androidx.lifecycle.LiveData
import com.example.myapplication.Task
import com.example.myapplication.data.local.TaskDao

class TasksRepository(private val taskDao: TaskDao) {
    val readAllData: LiveData<List<Task>> = taskDao.readAllData()

    suspend fun addUser(task: Task) {
        taskDao.addTask(task)
    }

    suspend fun addTaskList(task: List<Task>) {
        taskDao.addTaskList(task)
    }

    suspend fun deleteAll() {
        taskDao.deleteAll()
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
}