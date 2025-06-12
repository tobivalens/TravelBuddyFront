package com.example.travelbuddyapp.resources.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travelbuddyapp.ui.theme.SaralaFont
import com.example.travelbuddyapp.viewmodel.EventViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun EditEventScreen(eventId: Int, navController: NavController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf(" ") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var photo by remember { mutableStateOf("") }

    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale("es", "ES"))

    val viewModel: EventViewModel = viewModel()
    val event by viewModel.currentEvent
    val editFlag by viewModel.editFlag

    LaunchedEffect(eventId) {
        viewModel.getEventById(eventId)
    }

    LaunchedEffect(event) {
        event?.let{
            title = it.nombre
            description = it.descripcion
        }
    }

    if(editFlag){
        AlertDialog(
            onDismissRequest = {viewModel.setEditFlag(false)},
            title = {Text("Exito")},
            text = { Text("El evento fue editado correctamente.")},
            confirmButton = {
                TextButton(onClick = {
                    viewModel.setEditFlag(false)
                    navController.popBackStack()
                }) {
                    Text("Aceptar.")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F3F8))
    ) {
        Column {
            // Encabezado superior
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFA181FA))
                    .padding(start = 8.dp, top = 12.dp, bottom = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Modificar",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = SaralaFont
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text("¡Editemos:", fontSize = 26.sp, fontFamily = SaralaFont)
                Text(title, fontSize = 30.sp, fontWeight = FontWeight.Bold, fontFamily = SaralaFont)

                Spacer(modifier = Modifier.height(16.dp))

                LabeledFieldD ("Nombre", title, Icons.Default.Edit) { title = it }
                LabeledFieldD("Descripción", description, Icons.Default.Description) { description = it }

                DateFieldD("Día de inicio", startDate) { showStartDatePicker = true }
                DateFieldD("Día de finalización", endDate) { showEndDatePicker = true }

                LabeledFieldD("Foto", photo, Icons.Default.Image) { photo = it }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.editEvent(
                        eventId,
                        title,
                        description,
                        startDate,
                        endDate)},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA181FA)),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text("Guardar", color = Color.White, fontSize = 16.sp, fontFamily = SaralaFont)
                }
            }
        }

        // Barra inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color(0xFFA181FA))
                .height(60.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(horizontal = 48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.navigate("home") }) {
                    Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White)
                }
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
            }
        }
    }

    if (showStartDatePicker) {
        DatePickerModal(
            onDateSelected = {
                it?.let { millis -> startDate = formatter.format(java.util.Date(millis)) }
            },
            onDismiss = { showStartDatePicker = false }
        )
    }

    if (showEndDatePicker) {
        DatePickerModal(
            onDateSelected = {
                it?.let { millis -> endDate = formatter.format(java.util.Date(millis)) }
            },
            onDismiss = { showEndDatePicker = false }
        )
    }
}

@Composable
fun LabeledField(s: String, description: String, description1: ImageVector, content: () -> Unit) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LabeledFieldD(
    label: String,
    value: String,
    icon: ImageVector,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(
            text = label,
            color = Color(0xFFA181FA),
            fontWeight = FontWeight.SemiBold,
            fontFamily = SaralaFont
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(icon, contentDescription = null, tint = Color(0xFFA181FA))
            },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White,
                focusedBorderColor = Color(0xFFA181FA),
                unfocusedBorderColor = Color(0xFFD3D3D3)
            ),
            textStyle = LocalTextStyle.current.copy(fontFamily = SaralaFont)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateFieldD(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            color = Color(0xFFA181FA),
            fontWeight = FontWeight.SemiBold,
            fontFamily = SaralaFont
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = false, // Desactivado para evitar que reciba focus
                leadingIcon = {
                    Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color(0xFFA181FA))
                },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color(0xFFD3D3D3),
                    disabledLabelColor = Color.Gray,
                    disabledLeadingIconColor = Color(0xFFA181FA),
                    containerColor = Color.White
                ),
                textStyle = LocalTextStyle.current.copy(fontFamily = SaralaFont)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled = remember { derivedStateOf { datePickerState.selectedDateMillis != null } }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                },
                enabled = confirmEnabled.value
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
