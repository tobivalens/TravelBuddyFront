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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import com.example.travelbuddyapp.viewmodel.ActivityViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EditActivityScreen(
    actId: Int,
    onBack: () -> Unit,
    navController: NavController
) {

    val viewModel: ActivityViewModel = viewModel()
    val activity by viewModel.currentActivity

    LaunchedEffect(actId) {
        viewModel.loadActivity(actId)
    }

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
    val editFlag by viewModel.editFlag

    LaunchedEffect(activity) {
        activity?.let{
            title = it.nombre
            description = it.descripcion
        }
    }

    if(editFlag){
        AlertDialog(
            onDismissRequest = {viewModel.setEditFlag(false)},
            title = {Text("Exito")},
            text = { Text("La actividad se ha editado correctamente.")},
            confirmButton = {
                TextButton(onClick = {
                    viewModel.setEditFlag(false)
                    navController.navigate("home")
                }) {
                    Text("Aceptar.")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F7FC))
    ) {
        Column {
            // Header morado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFA181FA))
                    .padding(start = 6.dp, top = 6.dp, bottom = 4.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Modificar",
                        fontSize = 22.sp,
                        color = Color.White,

                        )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    "¡Editemos:",
                    fontSize = 26.sp,
                    color = Color(0xFF52545B),

                    )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    title,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF52545B),

                    )

                Spacer(modifier = Modifier.height(16.dp))

                LabeledFieldL("Nombre", title, Icons.Default.Edit) { title = it }
                LabeledFieldL("Descripción", description, Icons.Default.Description) { description = it }
                DateFieldL("Fecha", date) { showDatePicker = true }
                DateFieldL("Hora", time) { showTimePicker = true }
                LabeledFieldL("Ubicación", location, Icons.Default.Place) { location = it }
                LabeledFieldL("Foto", photo, Icons.Default.Image) { photo = it }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {viewModel.editActivity(
                        actId,
                        title,
                        description,
                        date,
                        time,
                        location)},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA181FA)),
                    shape = RoundedCornerShape(55.dp)
                ) {
                    Text(
                        "Guardar",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,

                        )
                }
            }
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

@Composable
fun TimePickerDialog(
    onTimeSelected: (String?) -> Unit,
    onDismiss: () -> Unit
) {

    var hour by remember { mutableStateOf(12) }
    var minute by remember { mutableStateOf(0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val timeStr = String.format("%02d:%02d", hour, minute)
                    onTimeSelected(timeStr)
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        text = {
            Column {
                Text("Selecciona hora")
                Spacer(modifier = Modifier.height(8.dp))
                Row {

                    Button(onClick = { if (hour > 0) hour-- }) { Text("-") }
                    Text(" $hour h ", modifier = Modifier.padding(horizontal = 8.dp))
                    Button(onClick = { if (hour < 23) hour++ }) { Text("+") }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(onClick = { if (minute > 0) minute -= 5 }) { Text("-") }
                    Text(" $minute m ", modifier = Modifier.padding(horizontal = 8.dp))
                    Button(onClick = { if (minute < 55) minute += 5 }) { Text("+") }
                }
            }
        }
    )
}

@Composable
fun LabeledFieldL(
    label: String,
    value: String,
    icon: ImageVector,
    onValueChange: (String) -> Unit
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
            )

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateFieldL(
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
                )
            )
        }
    }
}
