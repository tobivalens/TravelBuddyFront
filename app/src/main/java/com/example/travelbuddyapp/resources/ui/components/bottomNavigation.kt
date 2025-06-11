package com.example.travelbuddyapp.resources.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.travelbuddyapp.resources.icons.AppIcons
import com.example.travelbuddyapp.ui.theme.PurpleHeader
import androidx.compose.foundation.layout.size


@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar(
        containerColor = PurpleHeader,
        contentColor = Color.White
    ) {
        IconButton(
            onClick = { navController.navigate("home") },
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Icon(
                imageVector = if (currentRoute == "home") AppIcons.HomeSelected() else AppIcons.Home(),
                contentDescription = "Home",
                tint = Color.Unspecified,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f, true))

        IconButton(onClick = { navController.navigate("optionalAdd") }) {
            Icon(
                imageVector = if (currentRoute == "optionalAdd") AppIcons.addSelected() else AppIcons.add(),
                contentDescription = "Add",
                tint = Color.Unspecified,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f, true))

        IconButton(
            onClick = { navController.navigate("profile") },
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Icon(
                imageVector = if (currentRoute == "profile") AppIcons.profileSelected() else AppIcons.profile(),
                contentDescription = "Profile",
                tint = Color.Unspecified,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}