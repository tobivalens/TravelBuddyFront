package com.example.travelbuddyapp.resources.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.travelbuddyapp.viewmodel.ActivityViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityDetailScreen(
    actId: Int,
    navController: NavController,
    onBackClick: () -> Unit
) {
    val viewModel: ActivityViewModel = viewModel()
    val activity by viewModel.currentActivity

    LaunchedEffect(actId) {
        viewModel.loadActivity(actId)
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = activity?.nombre ?: "Detalle Actividad",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                actions = {
                    if (activity != null) {
                        IconButton(onClick = {
                            navController.navigate("editAct/${activity!!.id_actividad}")
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.White)
                        }
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFA181FA))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF7F7F7))
                .verticalScroll(scrollState)
        ) {
            AsyncImage(
                model = activity?.id_imagen ?: "https://via.placeholder.com/600x220.png?text=Imagen+actividad",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            DetailBlock("Descripción", activity?.descripcion ?: "Sin descripción")
            DetailBlock("Fecha", activity?.fecha_actividad?: "Sin fecha")
            DetailBlock("Hora", activity?.hora_actividad ?: "Sin hora")
            DetailBlock("Ubicación", activity?.ubicacion ?: "Sin ubicación")
            DetailBlock("Archivos adjuntos", "No hay archivos aún")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Eliminar actividad */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(40.dp)
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Eliminar actividad")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun DetailBlock(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(Color(0xFFB085F5))
            .padding(20.dp)
    ) {
        Text(text = label, fontSize = 12.sp, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = value, fontSize = 15.sp, color = Color.White)
    }
}



