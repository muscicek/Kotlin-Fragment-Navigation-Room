package com.example.projectexample.view.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [model::class], version = 2)
abstract class modelDatabase : RoomDatabase() {
    abstract fun modelDao(): modelDao
}