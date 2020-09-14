package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Task::class] , version = 1 , exportSchema = false)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TasksDatabase? = null


        @InternalCoroutinesApi
        fun getDatabase(context: Context): TasksDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TasksDatabase::class.java,
                    "taska database",
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance

            }
        }
    }
}