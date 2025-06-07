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
import com.example.travelbuddyapp.datasource.DTOS.ActivityDTO
import com.example.travelbuddyapp.ui.theme.SaralaFont

import com.example.travelbuddyapp.viewmodel.ActivityViewModel
import com.example.travelbuddyapp.viewmodel.EventViewModel

private val PurplePrimary = Color(0xFF9B69E7)
private val PurpleLight = Color(0xFFB085F5)


@Composable
fun ActivitiesScreen(
    navController: NavController,
    eventId: Int
    // Insertar nombre del viaje actual
) {
    val viewModel: ActivityViewModel = viewModel()
    val eventViewModel: EventViewModel = viewModel()
    var selectedTab by remember { mutableStateOf(2) }
    val activities by viewModel.activities
    val event by eventViewModel.currentEvent

    LaunchedEffect(eventId) {
        eventViewModel.getEventById(eventId)
        viewModel.loadActivities(eventId)
    }

    val eventName = event?.nombre ?: "Evento sin nombre"

    val tabs = listOf("Eventos", "Gastos", "Actividades")

    Scaffold(
        topBar = {
            ActivitiesTopBar(
                tripName = eventName,
                onBackClick = { navController.popBackStack() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("createActivity/${eventId}") },
                containerColor = PurplePrimary,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir actividad")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
        ) {
            Spacer(Modifier.height(10.dp))
            CustomTabBar (
                tabs = tabs,
                selectedTab = selectedTab,
                onTabSelected = { index ->
                    selectedTab = index
                    when (index) {
                        0 -> navController.navigate("VisualizeEvent")
                        1 -> navController.navigate("gastos")
                        2 -> navController.navigate("VisualizeActivity")
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
                    navController.navigate("actDetail/${activity.id_actividad}")
                }
            )
        }
    }
}

@Composable
fun ActivitiesTopBar(
    tripName: String,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(PurpleLight)
            .padding(horizontal = 16.dp, vertical = 25.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White
            )
        }
        Text(
            text = tripName,
            color = Color.White,
            fontSize = 26.sp,
            modifier = Modifier.padding( horizontal = 16.dp),
            fontFamily = SaralaFont
        )
    }
}

@Composable
fun MonthCalendar(month: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {

        Text(
            text = month,
            style = MaterialTheme.typography.titleMedium,
            color = PurplePrimary,
            fontFamily = SaralaFont
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Días de la semana
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su").forEach { day ->
                Text(
                    text = day,
                    color = Color.Gray,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Días del mes (ejemplo para julio 2025)
        val weeks = listOf(
            listOf("31", "1", "2", "3", "4", "5", "6"),
            listOf("7", "8", "9", "10", "11", "12", "13"),
            listOf("14", "15", "16", "17", "18", "19", "20"),
            listOf("21", "22", "23", "24", "25", "26", "27"),
            listOf("28", "29", "30", "31", "", "", "")
        )

        weeks.forEach { week ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                week.forEach { day ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (day == "25") PurpleLight else Color.Transparent)
                            .clickable { /* Seleccionar día */ }
                    ) {
                        if (day.isNotEmpty()) {
                            Text(
                                text = day,
                                color = if (day == "25") Color.White else Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActivitiesList(
    items: List<ActivityDTO>,
    onActivityClick: (ActivityDTO) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(items) { item ->
            ActivityItem(
                item = item,
                onClick = { onActivityClick(item) }
            )
        }
    }
}

@Composable
fun ActivityItem(
    item: ActivityDTO,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen (puedes reemplazar con AsyncImage si tienes URL)
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PurpleLight)
            ) {
                // Icono o imagen de la actividad
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    color = PurplePrimary,
                    fontFamily = SaralaFont
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Fecha de prueba",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}