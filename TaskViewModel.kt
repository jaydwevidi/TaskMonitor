package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class TaskViewModel(application: Application):AndroidViewModel(application) {
    val readAllData:LiveData<List<Task>>
    private val repository: TasksRepository
    init {
        val taskDao = TasksDatabase.getDatabase(application).taskDao()
        repository = TasksRepository(taskDao)
        readAllData = repository.readAllData
    }

    fun addTask(task : Task){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(task)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAll()
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteTask(task)
        }
    }

    fun addTaskList(task: List<Task>){
        viewModelScope.launch (Dispatchers.IO){
            repository.addTaskList(task)
        }
    }
}