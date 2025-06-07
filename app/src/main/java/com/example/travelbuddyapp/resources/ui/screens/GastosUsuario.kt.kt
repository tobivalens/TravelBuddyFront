package com.example.travelbuddyapp.resources.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.travelbuddyapp.resources.ui.components.BottomBar
import com.example.travelbuddyapp.resources.utils.formatMoney
import com.example.travelbuddyapp.ui.theme.CardBackground
import com.example.travelbuddyapp.ui.theme.PurpleHeader
import com.example.travelbuddyapp.ui.theme.Sarala
import com.example.travelbuddyapp.ui.theme.SoftText


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
    navController: NavController
) {
    val viajes = listOf("Viaje a la Montaña", "Viaje a la Playa")
    var viajeSeleccionado by remember { mutableStateOf<String?>(null) }

    val gastosTotales = 46097
    val gastosViaje = 8000
    val detallesGastos = listOf(
        "Hospedaje" to 4000,
        "Comida" to 2606,
        "Pasajes" to 1154,
        "Recuerdos" to 200,
        "Boletos museo" to 40
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header morado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PurpleHeader)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Mis Gastos",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Sarala
            )
        }

        // Card de gastos totales
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Sus gastos totales son:",
                    color = Color.White,
                    fontFamily = Sarala
                )
                Text(
                    text = "$ ${gastosTotales.toString().formatMoney()}",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Sarala
                )
            }
        }

        // Dropdown
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .clickable { /* mostrar dropdown si no usas ExposedDropdownMenuBox */ }
                .padding(12.dp)
        ) {
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = viajeSeleccionado ?: "Selecciona un viaje para ver sus gastos",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },

                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    viajes.forEach { viaje ->
                        DropdownMenuItem(
                            text = { Text(viaje, fontFamily = Sarala) },
                            onClick = {
                                viajeSeleccionado = viaje
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        // Si se selecciona un viaje
        if (viajeSeleccionado != null) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Total de gastos del viaje:",
                    fontFamily = Sarala,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Text(
                    text = "$ ${gastosViaje.toString().formatMoney()}",
                    fontFamily = Sarala,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Listado de Gastos",
                    fontFamily = Sarala,
                    color = PurpleHeader,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                detallesGastos.forEach { (categoria, valor) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = categoria,
                            fontFamily = Sarala,
                            color = SoftText
                        )
                        Text(
                            text = "$ ${valor.toString().formatMoney()}",
                            fontFamily = Sarala,
                            color = SoftText
                        )
                    }
                }
            }
        }

        // Bottom Navigation (opcional)
        Spacer(modifier = Modifier.weight(1f))
        BottomBar()
    }
}

