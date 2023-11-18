package com.labinot.bajrami.todoapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.labinot.bajrami.todoapp.utils.Constants.TABLE_NAME
import java.time.Instant
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Entity(tableName = TABLE_NAME)
data class Note (

    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,

    @ColumnInfo(name = "note_title")
    val title:String,

    @ColumnInfo(name = "note_description")
    val description:String,

    @ColumnInfo(name = "note_priority")
    val priority:Priority,

    @ColumnInfo(name = "note_entry_date")
    val entryDate:Date = Date.from(Instant.now())

)
