package com.example.projectexample.view.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface modelDao {

    @Query("SELECT * FROM model")
    fun getAll(): List<model>

    @Insert
    fun insert(model: model)

    @Delete
    fun delete(model: model)

    @Query("Select * from model where id = :id ")
    fun getmodelbyid(id:Int) : model

}