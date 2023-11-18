package com.labinot.bajrami.todoapp.navigations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.labinot.bajrami.todoapp.models.Action
import com.labinot.bajrami.todoapp.screens.DetailScreen
import com.labinot.bajrami.todoapp.utils.Constants.DETAIL_ARGUMENT_KEY
import com.labinot.bajrami.todoapp.utils.Constants.DETAIL_SCREEN
import com.labinot.bajrami.todoapp.viewModels.MyViewModel

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.detailComposable(
      myViewModel: MyViewModel,
    navigateToHomeScreen: (Action) -> Unit

){

    composable(

        route = DETAIL_SCREEN,
        arguments = listOf(navArgument(DETAIL_ARGUMENT_KEY){

            type = NavType.IntType
        }),
        enterTransition = {
            slideInHorizontally(
                 initialOffsetX = {fullWidth -> -fullWidth},
                animationSpec = tween(
                    durationMillis = 300
                )
            )
        }
    ){navBackStackEntry->

        val noteId = navBackStackEntry.arguments!!.getInt(DETAIL_ARGUMENT_KEY)

        LaunchedEffect(key1 = noteId){

            myViewModel.getSelectedNote(noteId = noteId)

        }

        val selectedNote by myViewModel.selectedNotes.collectAsState()
        
        LaunchedEffect(key1 = selectedNote ){


                selectedNote?.let { myViewModel.updateNote(snote = it) }

        }
        
      DetailScreen(navigateToHomeScreen = navigateToHomeScreen,
          myViewModel = myViewModel,
                   selectedNote = selectedNote)

    }

}