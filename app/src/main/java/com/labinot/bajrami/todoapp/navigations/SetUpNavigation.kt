package com.labinot.bajrami.todoapp.navigations

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.labinot.bajrami.todoapp.utils.Constants.SPLASH_SCREEN
import com.labinot.bajrami.todoapp.viewModels.MyViewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType")
@Composable
fun SetUpNavigation(
    navController: NavHostController
){

    val screen = remember(navController) {

        Screens(navController = navController)

    }

    val mViewModel = viewModel<MyViewModel>()
    NavHost(navController = navController,
        startDestination = SPLASH_SCREEN){

        splashComposable (

            navigateToHomeScreen = screen.splash
        )

          homeComposable(

              navigateToDetailScreen = screen.detail
          )

        detailComposable(
            myViewModel = mViewModel,
            navigateToHomeScreen = screen.home
        )


    }


}