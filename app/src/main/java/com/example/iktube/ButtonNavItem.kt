package com.example.iktube

import androidx.compose.ui.graphics.vector.ImageVector

data class ButtonNavItem(
    val name:String,
    val route:String,
    val icon : ImageVector,
    val badgeCount:Int  = 0
)
