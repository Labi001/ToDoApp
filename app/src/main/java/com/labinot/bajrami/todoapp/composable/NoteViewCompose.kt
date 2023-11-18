package com.labinot.bajrami.todoapp.composable


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.labinot.bajrami.todoapp.R
import com.labinot.bajrami.todoapp.data.Priority
import com.labinot.bajrami.todoapp.ui.theme.MediumPriorityColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteView(

    title:String,
    onTitleChange:(String) -> Unit,
    description:String,
    onDescriptionChange:(String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit

){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp)) {

        OutlinedTextField(modifier = Modifier
            .fillMaxWidth(),
            value =title,
            onValueChange = {onTitleChange(it)},
            label = {

                Text(text = stringResource(R.string.title))
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedTextColor = MediumPriorityColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                cursorColor = MediumPriorityColor,
                focusedIndicatorColor = MediumPriorityColor,
                focusedLabelColor = Color.Black,
            ),
            shape = RoundedCornerShape(6.dp)
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        PriorityDropDown(priority =priority ,
            onPrioritySelected = onPrioritySelected)

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(modifier = Modifier
            .fillMaxSize(),
            value = description,
            onValueChange = {onDescriptionChange(it)},
            label = {

                Text(
                    text = stringResource(R.string.description),
                   )
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = false,
            colors = TextFieldDefaults.colors(
                focusedTextColor = MediumPriorityColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedLabelColor = Color.Black,
                focusedIndicatorColor = MediumPriorityColor,
                cursorColor = MediumPriorityColor
            ),
            shape = RoundedCornerShape(6.dp)
        )
        
    }

}


@Preview
@Composable
private fun TaskContentPreview(){

    NoteView(
        title = "",
        onTitleChange = {},
        description = "",
        onDescriptionChange = {},
        priority = Priority.LOW,
        onPrioritySelected = {}
    )

}