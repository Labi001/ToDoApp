package com.labinot.bajrami.todoapp.navigations

import androidx.navigation.NavController
import com.labinot.bajrami.todoapp.models.Action
import com.labinot.bajrami.todoapp.utils.Constants.HOME_SCREEN
import com.labinot.bajrami.todoapp.utils.Constants.SPLASH_SCREEN

class Screens(navController: NavController) {

    val splash: () -> Unit = {

        navController.navigate(route = "home/${Action.NO_ACTION}"){

               popUpTo(SPLASH_SCREEN){inclusive = true}
        }
    }

    val home:(Action) -> Unit = { action ->

        navController.navigate(  route = "home/${action}"){

            popUpTo(HOME_SCREEN){inclusive = true}
        }
    }

    val detail:(Int) -> Unit = { noteID ->

        navController.navigate( route =  "detail/$noteID")
    }

}