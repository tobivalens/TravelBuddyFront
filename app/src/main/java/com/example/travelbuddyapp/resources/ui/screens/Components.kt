package com.example.travelbuddyapp.resources.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.travelbuddyapp.resources.icons.AppIcons
import com.example.travelbuddyapp.ui.theme.SaralaFont

@Composable
fun AppBottomNavigationBar(
    nestedNavController: NavController = rememberNavController(),
    selectedItem: Int = 1,
    onOptionClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        // Borde superior gris
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFF3A434F))
                .align(Alignment.TopStart)
        )

        var navigationItem by remember {
            mutableStateOf(0)
        }

        NavigationBar(
            containerColor = Color(0xFFA181FA),
            tonalElevation = 0.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = "visualizeEvent",
                    )
                },
                selected = selectedItem == 0,
                onClick = { nestedNavController.navigate("visualizeEvent"){launchSingleTop=true} },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color(0xFF3A434F),
                    indicatorColor = Color.Transparent,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color(0xFF3A434F)
                )
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.AddBox,
                        contentDescription = "",
                    )
                },

                selected = selectedItem == 1,
                onClick = {nestedNavController.navigate(""){launchSingleTop=true} },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color(0xFF3A434F),
                    indicatorColor = Color.Transparent,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color(0xFF3A434F)
                )
            )


            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "profile",
                    )
                },
                selected = selectedItem == 1,
                onClick = {nestedNavController.navigate("profile"){launchSingleTop=true} },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color(0xFF3A434F),
                    indicatorColor = Color.Transparent,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color(0xFF3A434F)
                )
            )
        }
    }
}


@Composable
fun CustomTabBar(
    tabs: List<String>,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEachIndexed { index, tab ->
            val isSelected = index == selectedTab
            Button(
                onClick = { onTabSelected(index) },
                shape = RoundedCornerShape(55),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color(0xFF8066D3) else Color.Transparent,
                    contentColor = if (isSelected) Color.White else Color(0xFF8066D3)
                ),
                border = if (isSelected) null else BorderStroke(0.dp, Color.Transparent),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
                elevation = null
            ) {
                Text(
                    text = tab,
                    fontFamily = SaralaFont,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun ParticipantItem(name: String) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = Color(0xFF8C69FF),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = name,
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { /* Opciones */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Opciones",
                    tint = Color.Gray
                )
            }
        }

        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
    }
}



@Composable
fun HomeBottomBar(
    onHomeClick: () -> Unit,
    onAddClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFFA181FA),
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(AppIcons.HomeSelected(), contentDescription = "Inicio") },
            selected = true,
            onClick = onHomeClick,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent, // sin fondo
                selectedIconColor = Color.White,    // ícono blanco al seleccionar
                unselectedIconColor = Color.White   // ícono blanco al no seleccionar
            )
        )
        NavigationBarItem(
            icon = { Icon(AppIcons.add(), contentDescription = "Nuevo") },
            selected = false,
            onClick = onAddClick,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White
            )
        )
        NavigationBarItem(
            icon = { Icon(AppIcons.profile(), contentDescription = "Perfil") },
            selected = false,
            onClick = onProfileClick,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White
            )
        )
    }
}
