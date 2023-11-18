package com.labinot.bajrami.todoapp.repository

import com.labinot.bajrami.todoapp.data.Note
import com.labinot.bajrami.todoapp.data.ToDoDao
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(private val toDoDao: ToDoDao) {

    suspend fun addNote(note: Note) = toDoDao.insertNote(note)
    suspend fun deleteNote(note: Note) = toDoDao.deleteNote(note)
    suspend fun updateNote(note: Note) = toDoDao.updateNote(note)

    fun getAllNotes(): Flow<List<Note>> = toDoDao.getAllNotes().flowOn(Dispatchers.IO).conflate()
    fun searchQuery(searchQuery:String) :Flow<List<Note>> = toDoDao.searchDataBase(searchQuery).flowOn(Dispatchers.IO).conflate()
    suspend fun deleteAllNotes() {

        toDoDao.deleteAllNotes()
    }
    fun getNoteById(noteId: Int) :Flow<Note> = toDoDao.getNotesById(noteId)
    fun sortByLowPriority():Flow<List<Note>> = toDoDao.sortByLowPriority().flowOn(Dispatchers.IO).conflate()
    fun sortByHighPriority():Flow<List<Note>> = toDoDao.sortByHighPriority().flowOn(Dispatchers.IO).conflate()

//    fun getSelectedNotes(noteId:Int):Flow<Note>{
//
//        return toDoDao.getNotesById(noteId)
//    }


}