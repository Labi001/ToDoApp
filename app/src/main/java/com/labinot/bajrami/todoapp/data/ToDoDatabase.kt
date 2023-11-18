package com.labinot.bajrami.todoapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.labinot.bajrami.todoapp.utils.DateConverter

@RequiresApi(Build.VERSION_CODES.O)
@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class ToDoDatabase: RoomDatabase(){

    abstract fun todoDao():ToDoDao

}
