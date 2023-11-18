package com.labinot.bajrami.todoapp.screens


import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.labinot.bajrami.todoapp.composable.DetailAppBar
import com.labinot.bajrami.todoapp.composable.NoteView
import com.labinot.bajrami.todoapp.data.Note
import com.labinot.bajrami.todoapp.data.Priority
import com.labinot.bajrami.todoapp.models.Action
import com.labinot.bajrami.todoapp.ui.theme.MilkColor
import com.labinot.bajrami.todoapp.viewModels.MyViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(
    selectedNote:Note?,
    myViewModel: MyViewModel,
    navigateToHomeScreen: (Action) -> Unit) {

    var id: Int by myViewModel.id
    var title: String by myViewModel.title
    var description: String by myViewModel.description
    var priority: Priority by myViewModel.priority

     val context = LocalContext.current


    BackHandler {

        navigateToHomeScreen(Action.NO_ACTION)
    }

    if(selectedNote == null){

        id = 0
        title = ""
        description = ""
        priority = Priority.LOW

    }


    Scaffold(modifier = Modifier.fillMaxSize(),
        containerColor = MilkColor,
        topBar = {

           DetailAppBar(
               selectedNote = selectedNote,
               navigateToHomeScreen = { action ->

                   if(action == Action.NO_ACTION) {


                     navigateToHomeScreen(action)
                   }else{

                       if( action == Action.UPDATE){

                           if(myViewModel.validateFields()){

                               val note = Note(
                                   id = id,
                                   title = title,
                                   description = description,
                                   priority = priority
                               )
                               myViewModel.updateNoteF(note = note)
                               navigateToHomeScreen(action)

                           }else{

                               showToast(context = context)
                           }


                       }

                       if(action == Action.DELETE){

                           val note = Note(
                               id = id,
                               title = title,
                               description = description,
                               priority = priority
                           )
                           myViewModel.deleteNote(note = note)
                           navigateToHomeScreen(action)

                       }

                       if(action == Action.ADD){

                           if(myViewModel.validateFields()){
                               val note = Note(
                                   title = title,
                                   description = description,
                                   priority = priority
                               )
                               myViewModel.addNoteF(note = note)
                               navigateToHomeScreen(action)

                           }else{

                               showToast(context = context)

                           }



                       }

                       title = ""
                       description = ""
                       priority = Priority.LOW

                   }

               },
                         )

        },

        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = it)
            ) {

                NoteView(
                    title = title,
                    onTitleChange = { tit->

                        myViewModel.updateTitle(tit)
                    },
                    description = description,
                    onDescriptionChange = { desc ->

                       myViewModel.description.value = desc
                    },
                    priority = priority,
                    onPrioritySelected = {prio ->

                       myViewModel.priority.value = prio
                    }
                )

            }

        }
    )


}


fun showToast(context: Context) {

    Toast.makeText(
        context,
        "Title or Description is Empty",
        Toast.LENGTH_SHORT
    ).show()

}