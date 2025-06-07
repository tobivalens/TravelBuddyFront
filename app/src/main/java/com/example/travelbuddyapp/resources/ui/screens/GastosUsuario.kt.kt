package com.example.travelbuddyapp.resources.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.navigation.NavController

data class Gasto(
    val categoria: String,
    val monto: Int
)

data class Viaje(
    val nombre: String,
    val gastos: List<Gasto>
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaGastosUsuario(
    navController: NavController,
) {
    val viajes = listOf(
        Viaje(
            "Viaje a la Montaña", listOf(
                Gasto("Hospedaje", 4000),
                Gasto("Comida", 2606),
                Gasto("Pasajes", 1154),
                Gasto("Recuerdos", 200),
                Gasto("Boletos museo", 40),
            )
        ),
        Viaje(
            "Viaje a la Playa", listOf(
                Gasto("Hospedaje", 3500),
                Gasto("Comida", 1800)
            )
        )
    )

    var viajeSeleccionado by remember { mutableStateOf<Viaje?>(null) }
    var expanded by remember { mutableStateOf(false) }

    val totalGeneral = viajes.sumOf { it.gastos.sumOf { gasto -> gasto.monto } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Mis Gastos", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta de gastos totales
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFB38BFF)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Sus gastos totales son:", color = Color.White)
                Text(
                    "$ ${"%d".format(totalGeneral)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown de selección de viaje
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = viajeSeleccionado?.nombre ?: "Selecciona un viaje para ver sus gastos",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                viajes.forEach { viaje ->
                    DropdownMenuItem(
                        text = { Text(viaje.nombre) },
                        onClick = {
                            viajeSeleccionado = viaje
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de gastos si hay selección
        viajeSeleccionado?.let { viaje ->
            val totalViaje = viaje.gastos.sumOf { it.monto }

            Text("Total de gastos del viaje: $${totalViaje}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Listado de Gastos", color = Color(0xFF7C4DFF), fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(8.dp))

            viaje.gastos.forEach { gasto ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(gasto.categoria)
                    Text("$ ${gasto.monto}")
                }
            }
        }
    }
}
