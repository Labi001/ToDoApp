package com.labinot.bajrami.todoapp.data

import androidx.compose.ui.graphics.Color
import com.labinot.bajrami.todoapp.ui.theme.HighPriorityColor
import com.labinot.bajrami.todoapp.ui.theme.LowPriorityColor
import com.labinot.bajrami.todoapp.ui.theme.MediumPriorityColor
import com.labinot.bajrami.todoapp.ui.theme.NonePriorityColor

enum class Priority(val color:Color) {

    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)

}