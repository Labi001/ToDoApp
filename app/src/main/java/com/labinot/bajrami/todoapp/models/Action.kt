package com.labinot.bajrami.todoapp.models

enum class Action {

    ADD,
    UPDATE,
    DELETE,
    DELETE_ALL,
    NO_ACTION

}

fun String?.toAction(): Action {

  return if(this.isNullOrEmpty()) Action.NO_ACTION
  else Action.valueOf(this)



}