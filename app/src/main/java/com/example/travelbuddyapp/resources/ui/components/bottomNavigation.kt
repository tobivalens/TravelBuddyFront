package com.example.travelbuddyapp.resources.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.travelbuddyapp.resources.icons.AppIcons
import com.example.travelbuddyapp.ui.theme.PurpleHeader

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar(
        containerColor = PurpleHeader,
        contentColor = Color.White // se ignora por los íconos al usar tint = Color.Unspecified
    ) {
        IconButton(onClick = { navController.navigate("home") }) {
            Icon(
                imageVector = if (currentRoute == "home") AppIcons.HomeSelected() else AppIcons.Home(),
                contentDescription = "Home",
                tint = Color.Unspecified
            )
        }

        Spacer(modifier = Modifier.weight(1f, true))

        IconButton(onClick = { navController.navigate("optionalAdd") }) {
            Icon(
                imageVector = if (currentRoute == "optionalAdd") AppIcons.addSelected() else AppIcons.add(),
                contentDescription = "Add",
                tint = Color.Unspecified
            )
        }

        Spacer(modifier = Modifier.weight(1f, true))

        IconButton(onClick = { navController.navigate("profile") }) {
            Icon(
                imageVector = if (currentRoute == "profile") AppIcons.profileSelected() else AppIcons.profile(),
                contentDescription = "Profile",
                tint = Color.Unspecified
            )
        }
    }
}