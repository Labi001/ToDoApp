package com.labinot.bajrami.todoapp.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.labinot.bajrami.todoapp.R
import com.labinot.bajrami.todoapp.ui.theme.MediumPriorityColor
import com.labinot.bajrami.todoapp.ui.theme.MilkColor

@Composable
fun DisplayAlertDialog(

    title:String,
    message:String,
    openDialog:MutableState<Boolean>,
    onYesPressed: () -> Unit
){

    if(openDialog.value){

        AlertDialog(onDismissRequest = {
                  openDialog.value = false
        },
            confirmButton = { 
                      
                            TextButton(
                                onClick = {
                                    onYesPressed.invoke()}) {
                                
                                Text(text = stringResource(R.string.yes),
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold)


                            }
                            
            },
            dismissButton = {

                TextButton(
                    onClick = {
                      openDialog.value = false}) {

                    Text(text = stringResource(R.string.no),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold)
                }

            },
            title = {
                Text(text = title,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize)
            },
            text = {

                Text(text = message,
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize)
            },
            containerColor = MilkColor)



    }


}