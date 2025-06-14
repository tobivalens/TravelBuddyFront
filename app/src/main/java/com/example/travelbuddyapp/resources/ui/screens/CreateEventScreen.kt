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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Image
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travelbuddyapp.ui.theme.SaralaFont
import com.example.travelbuddyapp.viewmodel.AuthViewModel
import com.example.travelbuddyapp.viewmodel.EventViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CreateEvent(
    navController: NavController,
    onHomeClick: () -> Unit,
    onAddClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val viewModel: EventViewModel = viewModel()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var photo by remember { mutableStateOf("") }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale("es", "ES"))
    val createFlag by viewModel.createFlag

    if(createFlag){
        AlertDialog(
            onDismissRequest = {viewModel.setCreateFlag(false)},
            title = {Text("Exito")},
            text = { Text("El evento fue creado correctamente.")},
            confirmButton = {
                TextButton(onClick = {
                    viewModel.setCreateFlag(false)
                    navController.popBackStack()
                }) {
                    Text("Aceptar.")
                }
            }
        )
    }

    Scaffold(
        bottomBar = {
            HomeBottomBar(
                onHomeClick = onHomeClick,
                onAddClick = onAddClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                            text = "Nuevo Evento",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = SaralaFont
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Text("¡Vamos a crear un ", fontSize = 26.sp, fontFamily = SaralaFont)
                    Text("nuevo evento!", fontSize = 30.sp, fontWeight = FontWeight.Bold, fontFamily = SaralaFont)

                    Spacer(modifier = Modifier.height(16.dp))

                    LabeledField111("Nombre", title, Icons.Default.Edit) { title = it }
                    LabeledField111("Descripción", description, Icons.Default.Description) { description = it }

                    DateField111("Día de inicio", startDate) { showStartDatePicker = true }
                    DateField111("Día de finalización", endDate) { showEndDatePicker = true }

                    LabeledField111("Foto", photo, Icons.Default.Image) { photo = it }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { viewModel.createEvent(
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
                        Text("Crear Evento", color = Color.White, fontSize = 16.sp, fontFamily = SaralaFont)
                    }
                }
            }
        }

        // Date pickers fuera del Scaffold
        if (showStartDatePicker) {
            DatePickerModal111(
                onDateSelected = {
                    it?.let { millis -> startDate = formatter.format(java.util.Date(millis)) }
                },
                onDismiss = { showStartDatePicker = false }
            )
        }

        if (showEndDatePicker) {
            DatePickerModal111(
                onDateSelected = {
                    it?.let { millis -> endDate = formatter.format(java.util.Date(millis)) }
                },
                onDismiss = { showEndDatePicker = false }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LabeledField111(
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
private fun DateField111(
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
fun DatePickerModal111(
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
