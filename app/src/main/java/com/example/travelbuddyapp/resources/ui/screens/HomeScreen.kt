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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.travelbuddyapp.resources.icons.AppIcons

// 1) Colores
private val PurplePrimary = Color(0xFF9B69E7)
private val PurpleLight   = Color(0xFFB085F5)

// 2) Modelo de datos
data class TravelItem(
    val id: String,
    val imageUrl: String,
    val title: String,
    val description: String
)

// 3) Pantalla principal
@Composable
fun HomeScreen(
    userName: String,
    tabs: List<String> = listOf("Todos", "Mis Viajes", "Otros"),
    travels: List<TravelItem>,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onSearchClick: () -> Unit,
    onTravelClick: (TravelItem) -> Unit,
    onHomeClick: () -> Unit,
    onAddClick: () -> Unit,
    onProfileClick: () -> Unit
) {

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
                items       = travels,
                onItemClick = onTravelClick
            )
        }
    }
}

// 4) Barra superior
@Composable
fun HomeTopBar(userName: String, onSearchClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(PurplePrimary)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text  = "¡Hola $userName!",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
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

// 5) Row de pestañas custom
@Composable
fun HomeTabRow(
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
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
                    fontSize = 14.sp
                )
            }
        }
    }
}

// 6) Contenedor blanco + lista
@Composable
fun TravelList(
    items: List<TravelItem>,
    onItemClick: (TravelItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Viajes",
            style = MaterialTheme.typography.titleLarge,
            color = PurplePrimary,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
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

// 7) Tarjeta de cada viaje
@Composable
fun TravelItemCard(
    item: TravelItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
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
                model           = item.imageUrl,
                contentDescription = item.title,
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
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = PurplePrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// 8) Barra de navegación inferior
@Composable
fun HomeBottomBar(
    onHomeClick: () -> Unit,
    onAddClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(AppIcons.HomeSelected(), contentDescription = "Inicio") },
            selected = true,
            onClick = onHomeClick
        )
        NavigationBarItem(
            icon = { Icon(AppIcons.add(), contentDescription = "Nuevo") },
            selected = false,
            onClick = onAddClick
        )
        NavigationBarItem(
            icon = { Icon(AppIcons.profile(), contentDescription = "Perfil") },
            selected = false,
            onClick = onProfileClick
        )
    }
}