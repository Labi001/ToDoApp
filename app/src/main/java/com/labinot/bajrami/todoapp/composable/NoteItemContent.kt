package com.labinot.bajrami.todoapp.composable

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.labinot.bajrami.todoapp.R
import com.labinot.bajrami.todoapp.data.Note
import com.labinot.bajrami.todoapp.data.Priority
import com.labinot.bajrami.todoapp.models.Action
import com.labinot.bajrami.todoapp.ui.theme.HighPriorityColor
import com.labinot.bajrami.todoapp.ui.theme.MilkColor
import com.labinot.bajrami.todoapp.utils.RequestState
import com.labinot.bajrami.todoapp.utils.SearchAppBarState
import com.labinot.bajrami.todoapp.utils.formatDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NoteItems(
    noteList: RequestState<List<Note>>,
    searchNoteList: RequestState<List<Note>>,
    searchAppBarState: SearchAppBarState,
    navigateToNoteScreen: (noteId: Int) -> Unit,
    onSwipeToDelete: (Action, Note) -> Unit,
    lowPriorityNotes: List<Note>,
    highPriorityNotes: List<Note>,
    sortState: RequestState<Priority>,
){

    if(sortState is RequestState.Success){

        when{

            searchAppBarState == SearchAppBarState.TRIGGERED ->{

                if(searchNoteList is RequestState.Success){

                    HandleListContent(tasks = searchNoteList.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToNoteScreen = navigateToNoteScreen)
                }

            }

            sortState.data == Priority.NONE -> {

                if(noteList is RequestState.Success){

                    HandleListContent(tasks = noteList.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToNoteScreen = navigateToNoteScreen)
                }

            }

            sortState.data == Priority.LOW -> {

                if(noteList is RequestState.Success){

                    HandleListContent(tasks = lowPriorityNotes,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToNoteScreen = navigateToNoteScreen)
                }

            }

            sortState.data == Priority.HIGH -> {

                if(noteList is RequestState.Success){

                    HandleListContent(tasks = highPriorityNotes,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToNoteScreen = navigateToNoteScreen)
                }

            }

        }


    }


}



@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HandleListContent(

    tasks: List<Note>,
    onSwipeToDelete: (Action, Note) ->Unit,
    navigateToNoteScreen: (noteId: Int) -> Unit

){


    if(tasks.isNotEmpty()){

        LazyColumn{


            items(items = tasks,
                key = { note ->
                    note.id
                }){ note->

               val dismissState = rememberDismissState()
                val dismissDirection = dismissState.dismissDirection
                val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)


                if(isDismissed && dismissDirection == DismissDirection.EndToStart){

                    val scope = rememberCoroutineScope()

                    scope.launch {

                        delay(300)
                        onSwipeToDelete(Action.DELETE, note)
                    }


                }

               val degrees by animateFloatAsState(
                   targetValue = if(dismissState.targetValue == DismissValue.Default)
                       0f
                   else -45f,
                   label = "",
               )

                var itemAppeared by remember {

                    mutableStateOf(false)
                }

                LaunchedEffect(key1 = true ){

                    itemAppeared = true
                }
                
                AnimatedVisibility(visible = itemAppeared && !isDismissed,
                    enter = expandVertically (

                        animationSpec = tween(

                            durationMillis = 300
                        )
                    ),
                    exit = shrinkVertically(

                        animationSpec = tween(

                            durationMillis = 300
                        )
                    )
                ) {

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(7.dp)){

                        SwipeToDismiss(
                            state = dismissState,
                            directions = setOf(DismissDirection.EndToStart),
                            background = { RedBackground(degrees = degrees)},
                            dismissContent = {

                                NoteCard(
                                    note = note,
                                    navigateToNoteScreen = navigateToNoteScreen)

                            })

                    }


                }
                

            }

        }


    }else{

        EmptyContent()
    }


}

@Composable
fun NoteCard(
    note: Note,
    navigateToNoteScreen: (noteId: Int) -> Unit
) {


    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                navigateToNoteScreen(note.id)
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(

            defaultElevation = 6.dp
        )

    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(note.priority.color)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 7.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = note.title,
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )


                   Text(text = formatDate(note.entryDate.time),
                       color = Color.DarkGray,
                       style = MaterialTheme.typography.bodySmall)

                }

            }

            Row(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(horizontal = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = note.description,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Log.d("ViewModel", "Title: ${note.title}, Description: ${note.description},")


                Canvas(modifier = Modifier.size(19.dp)) {

                    drawCircle(color = note.priority.color)
                }

            }

        }

    }

}

@Composable
fun EmptyContent(){

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MilkColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {


        Image(modifier = Modifier.size(120.dp),
            painter = painterResource(R.drawable.ic_sad),
            contentDescription = stringResource(R.string.empty_image) )

        Spacer(modifier = Modifier.height(7.dp))

        Text(text = "There isn't any note !",
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold)


    }

}

@Composable
fun RedBackground(degrees:Float){

    Box(modifier = Modifier
        .fillMaxSize()
        .clip(RoundedCornerShape(16.dp))
        .background(HighPriorityColor)
        .padding(15.dp),
        contentAlignment = Alignment.CenterEnd){

        Icon(modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.delete_icon),
            tint = Color.White)


    }

}


@Preview
@Composable
fun NoteItemCardPreview() {

   // NoteItemCard()

}


