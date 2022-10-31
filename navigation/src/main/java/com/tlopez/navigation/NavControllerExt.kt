package com.tlopez.navigation

import androidx.navigation.NavController

fun NavController.navigateClearBackstack(route: String) {
    this.navigate(route) {
        popUpTo(0)
    }
}

