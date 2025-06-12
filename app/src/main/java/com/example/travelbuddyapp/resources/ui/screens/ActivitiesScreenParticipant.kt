package com.example.travelbuddyapp.resources.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.travelbuddyapp.datasource.DTOS.ActivityDTO
import com.example.travelbuddyapp.resources.ui.components.BottomNavigationBar
import com.example.travelbuddyapp.ui.theme.SaralaFont

import com.example.travelbuddyapp.viewmodel.ActivityViewModel
import com.example.travelbuddyapp.viewmodel.EventViewModel

private val PurplePrimary = Color(0xFF9B69E7)

@Composable
fun ActivitiesScreenParticipant(
    navController: NavController,
    eventId: Int
) {
    val viewModel: ActivityViewModel = viewModel()
    val eventViewModel: EventViewModel = viewModel()
    val activities by viewModel.activities
    val event by eventViewModel.currentEvent

    LaunchedEffect(eventId) {
        eventViewModel.getEventById(eventId)
        viewModel.loadActivities(eventId)
    }

    val eventName = event?.nombre ?: "Evento sin nombre"

    val tabs = listOf("Eventos", "Gastos", "Actividades")

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val selectedTab = when {
        currentRoute?.startsWith("VisualizeEvent") == true -> 0
        currentRoute == "gastosUser" -> 1
        currentRoute?.startsWith("VisualizeActivities") == true -> 2
        else -> 0 // default
    }

    Scaffold(
        topBar = {
            ActivitiesTopBar(
                tripName = eventName,
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
        ) {
            Spacer(Modifier.height(12.dp))

            // Tabs
            CustomTabBar(
                tabs = tabs,
                selectedTab = selectedTab,
                onTabSelected = { index ->
                    when (tabs[index]) {
                        "Evento" -> navController.navigate("VisualizeEvent/${eventId}")
                        "Gastos" -> navController.navigate("gastosUser/${eventId}")
                        "Actividades" -> navController.navigate("VisualizeActivities/${eventId}")
                    }
                }
            )

            Spacer(Modifier.height(12.dp))

            MonthCalendar("Fecha de prueba")// En esta funcion debe de ir el mes actual

            Spacer(Modifier.height(12.dp))


            Text(
                text = "Próximas actividades",
                style = MaterialTheme.typography.titleLarge,
                color = PurplePrimary,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                fontFamily = SaralaFont
            )


            ActivitiesList(
                items = activities,
                onActivityClick = { activity ->
                    navController.navigate("actDetailUser/${activity.id_actividad}")
                }
            )
        }
    }
}