package com.example.travelbuddyapp.resources.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.travelbuddyapp.resources.ui.components.BottomNavigationBar
import com.example.travelbuddyapp.resources.utils.formatMoney
import com.example.travelbuddyapp.ui.theme.CardBackground
import com.example.travelbuddyapp.ui.theme.PurpleHeader
import com.example.travelbuddyapp.ui.theme.Sarala
import com.example.travelbuddyapp.ui.theme.SoftText

data class Gasto(val categoria: String, val monto: Int)
data class Viaje(val nombre: String, val gastos: List<Gasto>)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaGastosUsuario(
    navController: NavController,
    onBack: () -> Unit
) {
    // Tus datos reales:
    val viajes = listOf(
        Viaje("Viaje a la Montaña", listOf(
            Gasto("Hospedaje", 4000),
            Gasto("Comida", 2606),
            Gasto("Pasajes", 1154),
            Gasto("Recuerdos", 200),
            Gasto("Boletos museo", 40),
        )),
        Viaje("Viaje a la Playa", listOf(
            Gasto("Hospedaje", 3500),
            Gasto("Comida", 1800)
        ))
    )
    var viajeSeleccionado by remember { mutableStateOf<Viaje?>(null) }
    var expanded by remember { mutableStateOf(false) }

    // Cálculos
    val totalGeneral = viajes.sumOf { it.gastos.sumOf { gasto -> gasto.monto } }
    val totalViaje = viajeSeleccionado?.gastos?.sumOf { it.monto } ?: 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 1) HEADER MORADO con botón de back
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(PurpleHeader)
                .padding(16.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
            }
            Spacer(Modifier.width(8.dp))
            Text(
                "Mis Gastos",
                fontFamily = Sarala,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }

        Spacer(Modifier.height(16.dp))

        // 2) TARJETA GASTOS TOTALES
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Sus gastos totales son:",
                    fontFamily = Sarala,
                    color = Color.White
                )
                Text(
                    "$ ${totalGeneral.formatMoney()}",
                    fontFamily = Sarala,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Color.White
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // 3) DROPDOWN FULL-WIDTH
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
        ) {
            TextField(
                value = viajeSeleccionado?.nombre ?: "Selecciona un viaje para ver sus gastos",
                onValueChange = { },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                viajes.forEach { viaje ->
                    DropdownMenuItem(
                        text = { Text(viaje.nombre, fontFamily = Sarala) },
                        onClick = {
                            viajeSeleccionado = viaje
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // 4) DETALLE DE GASTOS DEL VIAJE SELECCIONADO
        viajeSeleccionado?.let { viaje ->
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                // Total viaje
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Total de gastos del viaje:",
                        fontFamily = Sarala,
                        fontSize = 16.sp,
                        color = SoftText
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        "$ ${totalViaje.formatMoney()}",
                        fontFamily = Sarala,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    "Listado de Gastos",
                    fontFamily = Sarala,
                    fontWeight = FontWeight.Bold,
                    color = PurpleHeader
                )

                Spacer(Modifier.height(8.dp))

                viaje.gastos.forEachIndexed { index, gasto ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AttachMoney,
                            contentDescription = null,
                            tint = PurpleHeader
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            gasto.categoria,
                            fontFamily = Sarala,
                            color = SoftText,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            "$ ${gasto.monto.formatMoney()}",
                            fontFamily = Sarala,
                            color = SoftText
                        )
                        Spacer(Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.MoreHoriz,
                            contentDescription = "Más",
                            tint = SoftText
                        )
                    }
                    if (index < viaje.gastos.lastIndex) {
                        Divider(color = Color.LightGray, thickness = 1.dp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // 5) BOTTOM BAR
        BottomNavigationBar(navController)
    }
}
