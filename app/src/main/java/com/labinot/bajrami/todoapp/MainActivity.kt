package com.labinot.bajrami.todoapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.labinot.bajrami.todoapp.navigations.SetUpNavigation
import com.labinot.bajrami.todoapp.ui.theme.MediumPriorityColor
import com.labinot.bajrami.todoapp.ui.theme.ToDoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppTheme {
            navController = rememberNavController()
                MyApp {

                    SetUpNavigation(navController = navController)
                }

            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit ={}) {

    val systemUiController = rememberSystemUiController()
    val isSystemInDarkMode = isSystemInDarkTheme()


    SideEffect {

        if (isSystemInDarkMode){

            systemUiController.setSystemBarsColor(

                color = Color.Black,
                darkIcons = false
            )

        }else {

            systemUiController.setSystemBarsColor(

                color = MediumPriorityColor,
                darkIcons = true
            )

        }


    }

    content()

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoAppTheme {

    }
}