package com.example.travelbuddyapp.resources.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelbuddyapp.viewmodel.ActivityViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CreateActivityScreen(
    eventId: Int,
    onBack: () -> Unit
) {

    val viewModel: ActivityViewModel = viewModel()
    val scrollState = rememberScrollState()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var photo by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale("es", "ES"))
    val timeFormatter = SimpleDateFormat("HH:mm", Locale("es", "ES"))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F7FC))
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFA181FA))
                .padding(start = 8.dp, top = 12.dp, bottom = 16.dp)
                .align(Alignment.TopStart), // Para que quede arriba
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "Nueva actividad",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.height(100.dp))

            Text("¡Vamos a crear una", fontSize = 32.sp, color = Color(0xFF52545B))

            Text(
                "nueva actividad!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF52545B)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LabeledField(
                "Nombre",
                title,
                Icons.Default.Edit,
                { title = it },
                "¿Cuál es el nombre de la actividad?"
            )
            LabeledField(
                "Descripción",
                description,
                Icons.Default.Description,
                { description = it },
                "¿Cuál es la descripción de la actividad?"
            )
            DateField(
                "Fecha",
                date,
                { showDatePicker = true },
                "¿Cuál será la fecha de la actividad"
            )
            DateField("Hora", time, { showTimePicker = true }, "¿Cuál es la hora de la actividad?")
            LabeledField(
                "Ubicación",
                location,
                Icons.Default.Place,
                { location = it },
                "¿Dónde se va a realizar la actividad?"
            )
            LabeledField(
                "Foto",
                photo,
                Icons.Default.Image,
                { photo = it },
                "Añade una foto del evento (opcional)"
            )

            Spacer(modifier = Modifier.height(5.dp))

            Button(
                onClick = {viewModel.createActivity(
                    eventId,
                    title,
                    description,
                    date,
                    time,
                    location
                    )},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA181FA)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Guardar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            // Este spacer agrega margen debajo del botón aunque se haga scroll
            Spacer(modifier = Modifier.height(15.dp))
        }
    }

    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = {
                it?.let { date = dateFormatter.format(Date(it)) }
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }

    if (showTimePicker) {
        TimePickerDialog(
            onTimeSelected = { selectedTime ->
                time = selectedTime ?: time
                showTimePicker = false
            },
            onDismiss = { showTimePicker = false }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabeledField(
    label: String,
    value: String,
    icon: ImageVector,
    onValueChange: (String) -> Unit,
    placeholder: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
    ) {
        Text(
            text = label,
            color = Color(0xFFA181FA),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .width(380.dp)
                .shadow(
                    elevation = 4.dp,
                    spotColor = Color(0x0F000000),
                    ambientColor = Color(0x0F000000)
                ),
            leadingIcon = {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color(0xFFA181FA)
                )
            },
            textStyle = TextStyle(
                textAlign = TextAlign.Start,
                fontSize = 16.sp
            ),
            shape = RoundedCornerShape(55.dp),
            singleLine = false,        // ← permite múltiples líneas
            maxLines = 6,              // ← opcional: limitar cuántas líneas puede crecer
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFA181FA),
                unfocusedBorderColor = Color(0xFFD3D3D3),
                focusedLeadingIconColor = Color(0xFFA181FA),
                unfocusedLeadingIconColor = Color(0xFFA181FA),
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                disabledTextColor = Color.Gray,
                cursorColor = Color(0xFFA181FA),
                focusedContainerColor = Color(0xFFFFFBFB),
                unfocusedContainerColor = Color(0xFFFFFBFB),
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xFFCBC7C7) // ← Color del placeholder aquí
                )
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateField(
    label: String,
    value: String,
    onClick: () -> Unit,
    placeholder: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding
    (vertical = 8.dp)
    ) {
        Text(
            text = label,
            color = Color(0xFFA181FA),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,

            )
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }) {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                modifier = Modifier
                    .width(380.dp)
                    .height(60.dp),
                enabled = false,
                leadingIcon = {
                    Icon(
                        Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = Color(0xFFA181FA)
                    )
                },
                shape = RoundedCornerShape(55.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color(0xFFFFFBFB),
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color(0xFFD3D3D3),
                    disabledLeadingIconColor = Color(0xFFA181FA)
                ),
                placeholder = {
                    Text(
                        text = placeholder,
                        color = Color(0xFFCBC7C7) // ← Color del placeholder aquí
                    )
                }
            )
        }
    }
}
