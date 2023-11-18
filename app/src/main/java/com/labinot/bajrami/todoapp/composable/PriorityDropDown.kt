package com.labinot.bajrami.todoapp.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.ContentAlpha
import androidx.wear.compose.material3.Text
import com.labinot.bajrami.todoapp.R
import com.labinot.bajrami.todoapp.data.Priority
import com.labinot.bajrami.todoapp.ui.theme.HighPriorityColor
import com.labinot.bajrami.todoapp.ui.theme.LowPriorityColor
import com.labinot.bajrami.todoapp.ui.theme.MediumPriorityColor
import com.labinot.bajrami.todoapp.ui.theme.NonePriorityColor

@Composable
fun PriorityDropDown (

    priority: Priority,
    onPrioritySelected:(Priority) -> Unit

){

    var expanded by remember {

        mutableStateOf(false)
    }

    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f, label = ""
    )

    var parentSize by remember {

        mutableStateOf(IntSize.Zero)
    }

    Row (modifier = Modifier
        .clip(
            shape = RoundedCornerShape(20.dp)
        )
        .fillMaxWidth()
        .onGloballyPositioned {

            parentSize = it.size
        }
        .background(Color.White)
        .height(60.dp)
        .clickable {
            expanded = true
        }
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled),
            shape = RoundedCornerShape(8.dp)
        ),
        verticalAlignment = Alignment.CenterVertically,
        ) {

        Canvas(modifier = Modifier
            .size(16.dp)
            .weight(1f),
             ){

            drawCircle(color = priority.color)

        }
        
        Text(modifier = Modifier.weight(8f),
            text = priority.name,
            color = Color.Black,
            style = MaterialTheme.typography.titleSmall)
        
        IconButton(modifier = Modifier
            .alpha(ContentAlpha.medium)
            .rotate(angle)
            .weight(1.5f),
            onClick = { expanded = true }) {

            Icon(imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = stringResource(R.string.arrow_down_image))
            
        }

        DropdownMenu(modifier = Modifier
            .width(with(LocalDensity.current) {parentSize.width.toDp()}),
            expanded = expanded,
            onDismissRequest = { expanded = false }) {

            DropdownMenuItem(text = {

                Text(text = "LOW",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleSmall)
            },

                leadingIcon = {

                    Canvas(modifier = Modifier.size(19.dp)) {

                        drawCircle(color = LowPriorityColor)
                    }

                },
                onClick = {
                    expanded = false
                    onPrioritySelected(Priority.LOW)
                })

            DropdownMenuItem(text = {

              Text(text = "HIGH",
                  color = Color.Black,
                  style = MaterialTheme.typography.titleSmall)
            },

                leadingIcon = {

                    Canvas(modifier = Modifier.size(19.dp)) {

                        drawCircle(color = HighPriorityColor)
                    }

                },
                onClick = {
                    expanded = false
                    onPrioritySelected(Priority.HIGH)
                })

            DropdownMenuItem(text = {

               Text(text = "MEDIUM",
                   color = Color.Black,
                   style = MaterialTheme.typography.titleSmall)
            },

                leadingIcon = {

                    Canvas(modifier = Modifier.size(19.dp)) {

                        drawCircle(color = MediumPriorityColor)
                    }

                },
                onClick = {
                    expanded = false
                    onPrioritySelected(Priority.MEDIUM)
                })

            DropdownMenuItem(text = {

                Text(text = "NONE",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleSmall)
            },

                leadingIcon = {

                    Canvas(modifier = Modifier.size(19.dp)) {

                        drawCircle(color = NonePriorityColor)
                    }

                },
                onClick = {
                    expanded = false
                    onPrioritySelected(Priority.NONE)
                })
            
        }

    }
}

@Preview
@Composable
fun PriorityDropDownPreview(){

    PriorityDropDown(priority = Priority.LOW,
        onPrioritySelected = {})

}