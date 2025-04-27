package com.anhar.atcadaptor.domain.model.bottomBar

import androidx.compose.runtime.Immutable

@Immutable
data class BottomBar(
    val title : String,
    val selectedIcon  : Int,
    val unselectedIcon : Int,
    val screen : Int
)
