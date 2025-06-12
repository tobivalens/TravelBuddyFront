package com.example.travelbuddyapp.resources.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travelbuddyapp.datasource.DTOS.EventResponse
import com.example.travelbuddyapp.resources.ui.components.BottomNavigationBar
import com.example.travelbuddyapp.viewmodel.AuthViewModel
import com.example.travelbuddyapp.viewmodel.EventViewModel
import kotlinx.coroutines.launch

private val PurpleLight   = Color(0xFFB085F5)

@Composable
fun HomeScreenOT(
    navController: NavController,
    userName: String,
    tabs: List<String> = listOf("Todos", "Mis Viajes", "Otros"),
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onSearchClick: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val viewModel: EventViewModel = viewModel()
    val events by viewModel.partEvents
    val authViewModel: AuthViewModel = viewModel()
    val userId by authViewModel.currentUserId

    LaunchedEffect(Unit) {
        viewModel.getParticipatedEvents()
        authViewModel.getUserId()
    }

    Scaffold(
        topBar    = { HomeTopBar(userName, onSearchClick) },
        bottomBar = { BottomNavigationBar(navController) },
        modifier   = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize().background(PurpleLight).padding(padding)
        ) {
            HomeTabRow(
                tabs           = tabs,
                selectedIndex  = selectedTab,
                onTabSelected  = onTabSelected
            )
            Spacer(modifier = Modifier.height(16.dp))
            TravelList(
                items       = events,
                onItemClick = { event ->
                    coroutineScope.launch {

                        if(event.id_administrador == userId) {
                            navController.navigate("VisualizeEventAdmin/${event.id_evento}")
                        }
                        else{
                            navController.navigate("VisualizeEvent/${event.id_evento}")
                        }
                    }
                }
            )
        }
    }
}