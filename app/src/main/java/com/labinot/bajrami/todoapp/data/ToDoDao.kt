package com.labinot.bajrami.todoapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * from todo_table ORDER BY id ASC")
    fun getAllNotes():Flow<List<Note>>

    @Query("SELECT * from todo_table WHERE id =:taskId")
    fun getNotesById(taskId: Int): Flow<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM todo_table WHERE note_title LIKE :searchQuery OR note_description LIKE :searchQuery")
    fun searchDataBase(searchQuery: String): Flow<List<Note>>

    @Query("SELECT * FROM todo_table ORDER BY CASE" +
            " WHEN note_priority LIKE 'L%' THEN 1 " +
            "WHEN note_priority LIKE 'M%' THEN 2" +
            " WHEN note_priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): Flow<List<Note>>

    @Query("SELECT * FROM todo_table ORDER BY CASE " +
            "WHEN note_priority LIKE 'H%' THEN 1 " +
            "WHEN note_priority LIKE 'M%' THEN 2 " +
            "WHEN note_priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): Flow<List<Note>>


}