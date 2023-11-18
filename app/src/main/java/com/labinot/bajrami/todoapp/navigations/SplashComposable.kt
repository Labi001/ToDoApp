package com.labinot.bajrami.todoapp.navigations

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import com.labinot.bajrami.todoapp.screens.SplashScreen
import com.labinot.bajrami.todoapp.utils.Constants.SPLASH_SCREEN
import androidx.navigation.NavGraphBuilder

import androidx.navigation.compose.composable


fun NavGraphBuilder.splashComposable(

    navigateToHomeScreen: () -> Unit

) {

    composable(

        route = SPLASH_SCREEN,
        exitTransition = {

                    slideOutVertically(
                        targetOffsetY = {-it},
                      animationSpec = tween(durationMillis = 300)
                    )

        }
    ) {

        SplashScreen(navigateToHomeScreen = navigateToHomeScreen)

    }
}

