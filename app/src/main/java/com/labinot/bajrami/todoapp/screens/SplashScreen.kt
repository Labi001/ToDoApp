package com.labinot.bajrami.todoapp.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.labinot.bajrami.todoapp.R
import com.labinot.bajrami.todoapp.ui.theme.MediumPriorityColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(

    navigateToHomeScreen: () -> Unit
){

    var startAnimation by remember{

        mutableStateOf(false)
    }

    val offsetState by animateDpAsState(
        targetValue = if(startAnimation) 0.dp else 100.dp,
        animationSpec = tween(

            durationMillis = 1000
        ), label = ""
    )

    val alphaState by animateFloatAsState(
        targetValue = if(startAnimation) 1f else 0f,
        animationSpec = tween(

            durationMillis = 1000
        ), label = ""
    )

    LaunchedEffect(key1 = true ){

        startAnimation = true
        delay(300)

        navigateToHomeScreen()

    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MediumPriorityColor),
        contentAlignment = Alignment.Center){

        Image(modifier = Modifier.size(100.dp)
            .offset(y = offsetState)
            .alpha(alpha = alphaState),
            painter = painterResource(R.drawable.splash_screen_ic),
            contentDescription = stringResource(R.string.splash_screen_image)
        )


    }

}

@Preview
@Composable
fun SplashScreenPreview(){

    SplashScreen(navigateToHomeScreen = {})
}