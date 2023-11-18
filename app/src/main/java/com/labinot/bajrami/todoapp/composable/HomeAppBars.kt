package com.labinot.bajrami.todoapp.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.ContentAlpha


import com.labinot.bajrami.todoapp.R
import com.labinot.bajrami.todoapp.data.Priority

import com.labinot.bajrami.todoapp.ui.theme.HighPriorityColor
import com.labinot.bajrami.todoapp.ui.theme.LowPriorityColor
import com.labinot.bajrami.todoapp.ui.theme.MediumPriorityColor
import com.labinot.bajrami.todoapp.ui.theme.NonePriorityColor
import com.labinot.bajrami.todoapp.utils.SearchAppBarState
import com.labinot.bajrami.todoapp.viewModels.MyViewModel
import kotlinx.coroutines.launch


@SuppressLint("RememberReturnType")
@Composable
fun NoteAppBar(

    viewModel: MyViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
    snackBarHostState: SnackbarHostState

) {

    val scope = rememberCoroutineScope()

    when (searchAppBarState) {

        SearchAppBarState.CLOSED -> {

            DefaultNoteAppBar(

                onSearchClicked = {

                    viewModel.searchAppBarState.value =
                        SearchAppBarState.OPENED

                },
                onSortClicked = {

                                viewModel.persistSortState(it)
                },
                onDeleteAllConfirm = {

                    viewModel.deleteAllNote()

                    scope.launch {

                        snackBarHostState.showSnackbar(

                            message = "Deleted All Notes !",
                            duration = SnackbarDuration.Short
                        )

                    }

                }
            )

        }

        else -> {

            SearchAppBar(text = searchTextState,
                onTextChange = { newText ->

                    viewModel.searchTextState.value = newText
                },
                onCloseClicked = {

                    viewModel.searchAppBarState.value =
                        SearchAppBarState.CLOSED
                    viewModel.searchTextState.value = ""
                },
                onSearchClicked = { query->

                    viewModel.searchDataBase(searchQuery = query)

                })

        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultNoteAppBar(

    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllConfirm: () -> Unit


) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(7.dp))
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 7.dp)
                .clip(RoundedCornerShape(20.dp))
                .shadow(12.dp, RoundedCornerShape(20.dp)),
            title = {
                Text(
                    text = stringResource(R.string.app_name),
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                )

            },

            actions = {

                ListAppBarActions(
                    onSearchClicked = onSearchClicked,
                    onSortClicked = onSortClicked,
                    onDeleteAllConfirm = onDeleteAllConfirm
                )


            },
            colors = topAppBarColors(
        containerColor = MediumPriorityColor,
)
        )
    }



}

@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllConfirm: () -> Unit
) {


    val openDialog = remember {

        mutableStateOf(false)
    }
    
    DisplayAlertDialog(title = stringResource(id = R.string.delete_all_task),
        message = stringResource(id = R.string.delete_all_note_confirmation),
        openDialog = openDialog,
        onYesPressed = {
            onDeleteAllConfirm.invoke()
            openDialog.value = false
        }
        )

    SearchActions(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onDeleteAllConfirm = {

        openDialog.value = true
    })

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(

    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit

) {

    val focusRequester = remember {

        FocusRequester()
    }




    LaunchedEffect(key1 = Unit){

         focusRequester.requestFocus()
    }

    Column {

        Spacer(modifier = Modifier.height(7.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 7.dp),
            shape = RoundedCornerShape(20.dp),
            color = Color.Transparent
        ) {

            TextField(modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
                value = text,
                onValueChange = {

                    onTextChange(it)
                },
                placeholder = {

                    Text(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        text = "Search Your Note",
                        color = Color.Black
                    )

                },
                textStyle = TextStyle(

                    color = Color.Black,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                ),
                singleLine = true,
                leadingIcon = {

                    IconButton(onClick = { /*TODO*/ }) {

                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(R.string.search_icon),
                            tint = Color.Black
                        )

                    }

                },
                trailingIcon = {

                    IconButton(onClick = {

                        if(text.isNotEmpty()){

                           onTextChange("")

                        }else {

                           onCloseClicked.invoke()
                        }

                    }) {

                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.close_icon),
                            tint = Color.Black
                        )

                    }


                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MediumPriorityColor,
                    unfocusedContainerColor = MediumPriorityColor,
                    disabledContainerColor = MediumPriorityColor,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),

                keyboardOptions = KeyboardOptions(

                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(

                    onSearch = {

                        onSearchClicked.invoke(text)
                    }
                )
            )

        }

    }


}

@Composable
fun DeleteAllAction(onDeleteAllConfirm: () -> Unit) {

    var expanded by remember { mutableStateOf(false) }

    Icon(imageVector = Icons.Filled.MoreVert,
        contentDescription = stringResource(R.string.more_icon),
        tint = Color.Black,
        modifier = Modifier
            .padding(end = 7.dp)
            .clickable {
                expanded = true
            }
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {

        DropdownMenuItem(text = {

            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = stringResource(R.string.delete_all),
                style = MaterialTheme.typography.titleSmall
            )
        },
            onClick = {
                expanded = false
                onDeleteAllConfirm.invoke()
            })


    }

}

@Composable
fun SortAction(onSortClicked: (Priority) -> Unit) {

    var expanded by remember { mutableStateOf(false) }

    Icon(painter = painterResource(R.drawable.ic_filter),
        contentDescription = "Filter Icon",
        tint = Color.Black,
        modifier = Modifier
            .clickable {
                expanded = true
            }
            .padding(end = 10.dp))

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {

        DropdownMenuItem(text = {

            Text(text = "LOW",
                style = MaterialTheme.typography.titleSmall)
        },

            leadingIcon = {

                Canvas(modifier = Modifier.size(19.dp)) {

                    drawCircle(color = LowPriorityColor)
                }

            },
            onClick = {
                expanded = false
                onSortClicked(Priority.LOW)
            })

        DropdownMenuItem(text = {

            Text(text = "HIGH",
                style = MaterialTheme.typography.titleSmall)
        },

            leadingIcon = {

                Canvas(modifier = Modifier.size(19.dp)) {

                    drawCircle(color = HighPriorityColor)
                }

            },
            onClick = {
                expanded = false
                onSortClicked(Priority.HIGH)
            })

        DropdownMenuItem(text = {

            Text(text = "MEDIUM",
                style = MaterialTheme.typography.titleSmall)
        },

            leadingIcon = {

                Canvas(modifier = Modifier.size(19.dp)) {

                    drawCircle(color = MediumPriorityColor)
                }

            },
            onClick = {
                expanded = false
                onSortClicked(Priority.MEDIUM)
            })

        DropdownMenuItem(text = {

            Text(text = "NONE",
                style = MaterialTheme.typography.titleSmall)
        },

            leadingIcon = {

                Canvas(modifier = Modifier.size(19.dp)) {

                    drawCircle(color = NonePriorityColor)
                }

            },
            onClick = {
                expanded = false
                onSortClicked(Priority.NONE)
            })

    }

}

@Composable
fun SearchActions(onSearchClicked: () -> Unit) {

    Icon(imageVector = Icons.Filled.Search,
        contentDescription = stringResource(R.string.search_icon),
        tint = Color.Black,
        modifier = Modifier
            .padding(end = 10.dp)
            .clickable {
                onSearchClicked.invoke()
            }
    )

}

@Composable
@Preview
private fun SearchAppBarPreview() {

    SearchAppBar(text = "",
        onTextChange = {},
        onCloseClicked = { },
        onSearchClicked = {})

}
