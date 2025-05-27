package com.example.travelbuddyapp.resources.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.travelbuddyapp.datasource.DTOS.EventResponse
import com.example.travelbuddyapp.resources.icons.AppIcons
import com.example.travelbuddyapp.ui.theme.SaralaFont
import com.example.travelbuddyapp.viewmodel.AuthViewModel
import com.example.travelbuddyapp.viewmodel.EventViewModel

private val PurplePrimary = Color(0xFF9B69E7)
private val PurpleLight   = Color(0xFFB085F5)

data class TravelItem(
    val id: String,
    val imageUrl: String,
    val title: String,
    val description: String
)

@Composable
fun HomeScreen(
    navController: NavController,
    userName: String,
    tabs: List<String> = listOf("Todos", "Mis Viajes", "Otros"),
    travels: List<TravelItem>,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onSearchClick: () -> Unit,
    onTravelClick: (EventResponse) -> Unit,
    onHomeClick: () -> Unit,
    onAddClick: () -> Unit,
    onProfileClick: () -> Unit
) {

    val viewModel: EventViewModel = viewModel()
    val events by viewModel.events

    LaunchedEffect(Unit) {
        viewModel.getAllEvents()
    }

    Scaffold(
        topBar    = { HomeTopBar(userName, onSearchClick) },
        bottomBar = { HomeBottomBar(onHomeClick, onAddClick, onProfileClick) },
        modifier   = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PurpleLight)
                .padding(padding)
        ) {
            HomeTabRow(
                tabs           = tabs,
                selectedIndex  = selectedTab,
                onTabSelected  = onTabSelected
            )
            Spacer(modifier = Modifier.height(16.dp))
            TravelList(
                items       = events,
                onItemClick = {event ->
                    navController.navigate("VisualizeEvent/${event.id_evento}")
                }
            )
        }
    }
}

@Composable
fun HomeTopBar(userName: String, onSearchClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(PurpleLight)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text  = "¡Hola $userName!",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 25.sp,
            fontFamily = SaralaFont

        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onSearchClick) {
            Icon(
                imageVector    = AppIcons.search(),
                contentDescription = "Buscar",
                tint           = Color.White
            )
        }
    }
}

@Composable
fun HomeTabRow(
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        tabs.forEachIndexed { index, label ->
            val isSelected = index == selectedIndex
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(if (isSelected) Color.White else PurplePrimary.copy(alpha = 0.3f))
                    .clickable { onTabSelected(index) }
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text  = label,
                    color = if (isSelected) PurplePrimary else Color.White,
                    fontSize = 14.sp,
                    fontFamily = SaralaFont
                )
            }
        }
    }
}

@Composable
fun TravelList(
    items: List<EventResponse>,
    onItemClick: (EventResponse) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White, shape = RoundedCornerShape(topStart = 55.dp, topEnd = 55.dp))
            .padding(top = 10.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Viajes",
            style = MaterialTheme.typography.titleLarge,
            color = PurplePrimary,
            modifier = Modifier.padding(horizontal = 35.dp, vertical = 7.dp),
            fontFamily = SaralaFont
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(items) { item ->
                TravelItemCard(item = item, onClick = { onItemClick(item) })
            }
        }
    }
}

@Composable
fun TravelItemCard(
    item: EventResponse,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp)
        ) {
            AsyncImage(
                model           = "https://raw.githubusercontet.com/tomoewinds/devasc-study-team/blob/master/resumen-superficie-y-texturas-de-muro-de-piedra-de-hormigon-blanco.jpg",
                contentDescription = item.nombre,
                contentScale    = ContentScale.Crop,
                modifier = Modifier
                    .size(84.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    color = PurplePrimary,
                    fontFamily = SaralaFont

                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = SaralaFont
                )
            }
        }
    }
}
