package com.example.projectexample.view.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity
class model(

    @ColumnInfo(name ="image")var image:ByteArray?,
    @ColumnInfo(name = "message") var message:String,

) {
    @PrimaryKey(autoGenerate = true)
    var id=0
}