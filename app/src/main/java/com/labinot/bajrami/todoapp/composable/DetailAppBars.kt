package com.labinot.bajrami.todoapp.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.labinot.bajrami.todoapp.R
import com.labinot.bajrami.todoapp.data.Note
import com.labinot.bajrami.todoapp.models.Action

import com.labinot.bajrami.todoapp.ui.theme.MediumPriorityColor

@Composable
fun DetailAppBar(
    selectedNote: Note?,
    navigateToHomeScreen: (Action) -> Unit
) {


    if(selectedNote == null){

        NewNotesAppBar(navigateToHomeScreen=navigateToHomeScreen)

    }else{

        ExistingNotesAppBar(
            navigateToHomeScreen = navigateToHomeScreen,
            sNote = selectedNote
        )

    }





}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewNotesAppBar(
    navigateToHomeScreen: (Action) -> Unit) {

    TopAppBar(modifier = Modifier
        .fillMaxWidth()
        .padding(7.dp)
        .clip(RoundedCornerShape(20.dp)),

        title = {

            Text(text = "New Note")
        },
        colors = topAppBarColors(
        containerColor = MediumPriorityColor
        ),

        navigationIcon = {

            BackAction(onBackClicked = navigateToHomeScreen)

        },

        actions = {

            AddAction(onAddClicked = navigateToHomeScreen)


        }

    )

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExistingNotesAppBar(
    navigateToHomeScreen: (Action) -> Unit,
    sNote: Note) {

    TopAppBar(modifier = Modifier
        .fillMaxWidth()
        .padding(7.dp)
        .clip(RoundedCornerShape(20.dp)),

        title = {

            Text(text = sNote.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        },
        colors = topAppBarColors(
        containerColor = MediumPriorityColor,
        ),

        navigationIcon = {

                         CloseAction(CloseClicked = navigateToHomeScreen)

        },

        actions = {

           ExistingAppBarActions(navigateToHomeScreen = navigateToHomeScreen,
               sNote = sNote)



        }

    )

}

@Composable
fun ExistingAppBarActions(
    navigateToHomeScreen: (Action) -> Unit,
    sNote: Note
){

    val openDialog = remember {

        mutableStateOf(false)
    }

    DisplayAlertDialog(title = stringResource(
        id = R.string.delete_note,
        sNote.title
    ),
        message = stringResource(
            id = R.string.delete_note_confirmation,
            sNote.title),
        openDialog = openDialog,
        onYesPressed = {navigateToHomeScreen(Action.DELETE)})

    DeleteAction(DeleteClicked = {

        openDialog.value = true
    })
    UpdateAction(UpdateClicked = navigateToHomeScreen)

}

@Composable
fun DeleteAction(

    DeleteClicked: (Action) -> Unit
) {

    IconButton(onClick = {
        DeleteClicked.invoke(Action.DELETE)
    }) {

        Icon(imageVector = Icons.Default.Delete,
            contentDescription = stringResource(R.string.delete_icon),
            tint = Color.Black
        )

    }
}

@Composable
fun UpdateAction(

    UpdateClicked: (Action) -> Unit
) {

    IconButton(onClick = {
        UpdateClicked.invoke(Action.UPDATE)
    }) {

        Icon(imageVector = Icons.Default.Check,
            contentDescription = stringResource(R.string.update_icon),
            tint = Color.Black
        )

    }
}

@Composable
fun CloseAction(
    CloseClicked: (Action) -> Unit
){

    IconButton(onClick = {
       CloseClicked.invoke(Action.NO_ACTION)
    }) {

        Icon(imageVector = Icons.Default.Close,
            contentDescription = "Close Icon",
            tint = Color.Black
        )

    }

}

@Composable
fun BackAction(
    onBackClicked: (Action) -> Unit
){

    IconButton(onClick = {
       onBackClicked.invoke(Action.NO_ACTION)
    }) {

        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.icon_back)
        )

    }

}

@Composable
fun AddAction(
    onAddClicked: (Action) -> Unit
){

    IconButton(onClick = {
        onAddClicked.invoke(Action.ADD)
    }) {

        Icon(imageVector = Icons.Filled.Check,
            contentDescription = stringResource(R.string.icon_add),
            tint = Color.Black
        )

    }

}

@Preview
@Composable
fun PreviewNoteAppBar(){

  // NotesAppBar()
}