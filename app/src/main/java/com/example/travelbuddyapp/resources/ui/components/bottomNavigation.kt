package com.example.travelbuddyapp.resources.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.travelbuddyapp.ui.theme.PurpleHeader

@Composable
fun BottomBar() {
    BottomAppBar(
        containerColor = PurpleHeader,
        contentColor = Color.White
    ) {
        IconButton(onClick = {}) { Icon(Icons.Default.Home, contentDescription = "Home") }
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = {}) { Icon(Icons.Default.Add, contentDescription = "Add") }
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = {}) { Icon(Icons.Default.Person, contentDescription = "Profile") }
    }
}
