package com.example.projectexample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.projectexample.R
import com.example.projectexample.view.model.modelDao
import com.example.projectexample.view.model.modelDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       }
}