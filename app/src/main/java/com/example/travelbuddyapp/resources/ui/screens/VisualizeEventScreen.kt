package com.example.travelbuddyapp.resources.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.travelbuddyapp.R
import com.example.travelbuddyapp.ui.theme.SaralaFont
import com.example.travelbuddyapp.viewmodel.EventViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisualizeEventScreen(
    eventId: Int,
    onBackClick: () -> Unit = {},
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val tabs = listOf("Evento", "Gastos", "Actividades")
    val viewModel: EventViewModel = viewModel()
    val event by viewModel.currentEvent
    val participants by viewModel.participants

    LaunchedEffect(eventId) {
        viewModel.getEventById(eventId)
        viewModel.loadParticipants(eventId)
    }

    val eventTitle = event?.nombre?: "Sin nombre"
    val description = event?.descripcion?: "Sin descripcion"
    val startDate = event?.fecha_inicio?: "Sin fecha"
    val endDate = event?.fecha_fin?: "Sin fecha"
    val unionCode = event?.codigo_union?: "Sin codigo"

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
            TopAppBar(
                title = {
                    Text(
                        text = eventTitle,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontFamily = SaralaFont,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFFA181FA)
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
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


            Spacer(Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_logo), // reemplaza con tu imagen
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(16.dp))

            // la descripcion
            Text(
                text = description,
                color = Color(0xFFCBC7C7),
                fontSize = 12.sp,
                fontFamily = SaralaFont
            )

            Spacer(Modifier.height(24.dp))

            // Fechas estaticas por ahora
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Fecha de inicio", color = Color(0xFFA38AFB), fontWeight = FontWeight.Bold)
                    Text(startDate, color = Color.Gray)
                }
                Column {
                    Text("Fecha de finalización", color = Color(0xFFA38AFB), fontWeight = FontWeight.Bold)
                    Text(endDate, color = Color.Gray)
                }
            }

            Spacer(Modifier.height(24.dp))

            // lista users
            Text("Participantes", color = Color(0xFFA38AFB), fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(8.dp))

            participants.forEach {
                ParticipantItem(name = it)
            }

            Spacer(Modifier.height(8.dp))

            OutlinedButton(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, Color(0xFFA38AFB))
            ) {
                Text("Código de Evento: $unionCode", color = Color(0xFFA38AFB))
            }

            Spacer(Modifier.height(24.dp))
            //ARCHIVOS
            Text(
                text = "Archivos",
                color = Color(0xFF8C69FF),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Comparte aquí fotos, documentos o cualquier archivo importante del viaje. Todo lo que subas estará disponible para que todos los participantes lo vean y accedan fácilmente.",
                color = Color.Gray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.InsertDriveFile,
                        contentDescription = "Archivo",
                        tint = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "No hay archivos aún",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8C69FF)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text(text = "Subir", color = Color.White)
            }
        }
    }
}
