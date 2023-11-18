package com.labinot.bajrami.todoapp.navigations

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.labinot.bajrami.todoapp.models.Action
import com.labinot.bajrami.todoapp.models.toAction
import com.labinot.bajrami.todoapp.screens.HomeScreen
import com.labinot.bajrami.todoapp.utils.Constants.HOME_ARGUMENT_KEY
import com.labinot.bajrami.todoapp.utils.Constants.HOME_SCREEN
import com.labinot.bajrami.todoapp.viewModels.MyViewModel

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.homeComposable(

    navigateToDetailScreen: (Int) -> Unit

){

    composable(

        route = HOME_SCREEN,
        arguments = listOf(navArgument(HOME_ARGUMENT_KEY){

            type = NavType.StringType
        })
    ){ navBackStackEntry ->

        val action = navBackStackEntry.arguments?.getString(HOME_ARGUMENT_KEY).toAction()

        var myAction by rememberSaveable {

            mutableStateOf(Action.NO_ACTION)
        }

        Log.d("HomeComposable: ",action.name)
        val mViewModel = hiltViewModel<MyViewModel>()
        LaunchedEffect(key1 = myAction){

            if(action != myAction){

                myAction = action
                mViewModel.action.value = action
            }


        }
              HomeScreen(navigateToDetailScreen = navigateToDetailScreen)
    }

}