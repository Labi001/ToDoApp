package com.labinot.bajrami.todoapp.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.labinot.bajrami.todoapp.data.Note
import com.labinot.bajrami.todoapp.data.Priority
import com.labinot.bajrami.todoapp.models.Action
import com.labinot.bajrami.todoapp.repository.DataStoreRepository
import com.labinot.bajrami.todoapp.repository.ToDoRepository
import com.labinot.bajrami.todoapp.utils.RequestState
import com.labinot.bajrami.todoapp.utils.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository)
    :ViewModel() {

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)

    val searchTextState: MutableState<String> =
        mutableStateOf("")

    private var _searchNotes = MutableStateFlow<RequestState<List<Note>>> (RequestState.Idle)
    val searchNotes = _searchNotes.asStateFlow()

    private var _allNotes = MutableStateFlow<RequestState<List<Note>>> (RequestState.Idle)
    val allNotes = _allNotes.asStateFlow()

    val lowPriorityNote: StateFlow<List<Note>> =
        repository.sortByLowPriority().stateIn(

            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    val highPriorityNote: StateFlow<List<Note>> =
        repository.sortByHighPriority().stateIn(

            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    private var _sortState =
        MutableStateFlow<RequestState<Priority>>(RequestState.Idle)

     val sortState:StateFlow<RequestState<Priority>> = _sortState


    val id: MutableState<Int> = mutableIntStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)

//    fun deleteNoteCHGT(note: Note) = viewModelScope.launch {
//        try {
//            repository.deleteNote(note)
//            // Optionally, you can remove the deleted note from the current list
//            val currentNotes = _allNotes.value
//            val updatedNotes = currentNotes.filterNot { it == note }
//            _allNotes.value = RequestState.Success(updatedNotes)
//        } catch (e: Exception) {
//            _allNotes.value = RequestState.Error(e)
//            Log.e("MyViewModel", "Error deleting note: ${e.message}")
//        }
//    }

    fun readSortState(){

        _sortState.value = RequestState.Loading

        try {

            viewModelScope.launch (Dispatchers.IO) {

                dataStoreRepository.readSortState
                    .map { Priority.valueOf(it) }
                    .collect{

                       _sortState.value = RequestState.Success(it)
                    }

            }


        } catch (e:Exception){

            _allNotes.value = RequestState.Error(e)
        }


    }

    fun persistSortState(priority: Priority){

        viewModelScope.launch(Dispatchers.IO) {

            dataStoreRepository.persistSortState(priority = priority)
        }

    }


    fun searchDataBase(searchQuery:String){

       _searchNotes.value = RequestState.Loading
        try {

            viewModelScope.launch {
                repository.searchQuery(searchQuery = "%$searchQuery%").collect{ searchNote ->

                    _searchNotes.value = RequestState.Success(searchNote)

                }
            }


        } catch (e:Exception){

            _searchNotes.value = RequestState.Error(e)
        }
         searchAppBarState.value = SearchAppBarState.TRIGGERED
    }

    init {

        _allNotes.value = RequestState.Loading

        try {

            viewModelScope.launch (Dispatchers.IO) {

                repository.getAllNotes().distinctUntilChanged()
                    .collect{ listOfNotes ->

                        if(listOfNotes.isEmpty()){

                            Log.d("Empty","Empty List")
                        }else{

                            _allNotes.value = RequestState.Success(listOfNotes)
                        }


                    }

            }


        } catch (e:Exception){

            _allNotes.value = RequestState.Error(e)
        }



    }



    private val _selectedNote: MutableStateFlow<Note?> = MutableStateFlow(null)
    val selectedNotes: StateFlow<Note?> = _selectedNote

    fun getSelectedNote(noteId:Int) = viewModelScope.launch{

        repository.getNoteById(noteId = noteId).collect{ note->


            _selectedNote.value = note
        }
    }


    fun addNoteF (note: Note) = viewModelScope.launch {

        repository.addNote(note)

        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    fun updateNoteF (note: Note) = viewModelScope.launch {

         repository.updateNote(note = note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch{

        repository.deleteNote(note)
    }

    fun deleteAllNote(){

        viewModelScope.launch {

            repository.deleteAllNotes()

           _allNotes.value = RequestState.Success(emptyList())
        }

    }






    fun updateNote(snote: Note){

        id.value = snote.id
        title.value = snote.title
        description.value = snote.description
        priority.value = snote.priority

    }

    fun updateTitle(newTitle:String){

        if(newTitle.length < 20){

            title.value = newTitle
        }

    }

    fun validateFields():Boolean{

        return title.value.isNotEmpty() && description.value.isNotEmpty()
    }



}