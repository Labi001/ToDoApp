package com.labinot.bajrami.todoapp.screens


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.labinot.bajrami.todoapp.composable.NoteAppBar
import com.labinot.bajrami.todoapp.composable.NoteItems
import com.labinot.bajrami.todoapp.models.Action
import com.labinot.bajrami.todoapp.ui.theme.MediumPriorityColor
import com.labinot.bajrami.todoapp.ui.theme.MilkColor
import com.labinot.bajrami.todoapp.utils.SearchAppBarState
import com.labinot.bajrami.todoapp.viewModels.MyViewModel
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navigateToDetailScreen: (Int) -> Unit) {

    val mViewModel = hiltViewModel<MyViewModel>()
    val searchAppBarState: SearchAppBarState by mViewModel.searchAppBarState


    LaunchedEffect(key1 = true ){

        mViewModel.readSortState()

    }
    val searchTextState: String by mViewModel.searchTextState

    val snackBarHostState = remember {

        SnackbarHostState()
    }

    val getAllNotes = mViewModel.allNotes.collectAsState().value
    val searchedNotes = mViewModel.searchNotes.collectAsState().value
    val sortState = mViewModel.sortState.collectAsState().value
    val lowPriorityNotes = mViewModel.lowPriorityNote.collectAsState().value
    val highPriorityNotes = mViewModel.highPriorityNote.collectAsState().value

    val action by mViewModel.action


    DisplaySnackBar(
        snackbarHostState = snackBarHostState,
        action = action
    )

    Scaffold (
        snackbarHost = { SnackbarHost(hostState = snackBarHostState)},
        modifier = Modifier.fillMaxSize(),
        containerColor = MilkColor,
        topBar = {

            NoteAppBar(viewModel = mViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState,
                snackBarHostState = snackBarHostState)
        },

        floatingActionButton = {
            FabList(
                onFabClicked = navigateToDetailScreen
               )


        },

        content = {

            Column(modifier = Modifier
                .padding(7.dp)
                .fillMaxSize()
                .padding(paddingValues = it),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top) {


               NoteItems(noteList = getAllNotes,
                   searchNoteList = searchedNotes,
                   searchAppBarState = searchAppBarState,
                   navigateToNoteScreen = navigateToDetailScreen,
                   onSwipeToDelete = { action, note ->



                       mViewModel.action.value = action
                       mViewModel.updateNoteF(note = note)
                       snackBarHostState.currentSnackbarData?.dismiss()


                   },
                   lowPriorityNotes = lowPriorityNotes,
                   highPriorityNotes = highPriorityNotes,
                   sortState = sortState
                 )

            }

        }


    )



}





@Composable
fun FabList(
    onFabClicked: (noteId: Int) -> Unit
           ) {


    FloatingActionButton(onClick = {
        onFabClicked( -1)
    },
        containerColor = MediumPriorityColor,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(

            defaultElevation = 8.dp
        )) {

        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = Icons.Filled.Add,
            contentDescription = "Add Button",
            tint = Color.Black)

    }

}

@SuppressLint("SuspiciousIndentation")
@Composable
fun DisplaySnackBar(

    snackbarHostState: SnackbarHostState,
    action: Action

){


    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action ){

        when (action) {
            Action.ADD -> {

                scope.launch {

                    snackbarHostState.showSnackbar(

                        message = "Your Note is Added !",
                        actionLabel = "OK"
                    )

                }

            }
            Action.UPDATE -> {

                scope.launch {

                    snackbarHostState.showSnackbar(

                        message = "Your Note is Updated !",
                        actionLabel = "OK"
                    )

                }



            }
            Action.DELETE -> {

                scope.launch {

                snackbarHostState.showSnackbar(

                        message = "Your Note is Deleted !",
                        actionLabel = "OK"
                    )

                }



            }

            else -> {
            }
        }

    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
private fun HomeScreenPreview(){
    
    HomeScreen(navigateToDetailScreen = {})
}