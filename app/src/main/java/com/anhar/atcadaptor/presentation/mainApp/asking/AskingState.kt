package com.anhar.atcadaptor.presentation.mainApp.asking

data class AskingState(
    val isLoading : Boolean = false ,
    val error : String ? = null ,
    val objectValue : String ="" ,
    val askingValue : String ="" ,
    val success :Boolean = false
)
