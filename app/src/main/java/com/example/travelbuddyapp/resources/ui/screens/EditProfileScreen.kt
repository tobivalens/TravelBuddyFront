package com.example.travelbuddyapp.resources.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travelbuddyapp.resources.ui.components.BottomNavigationBar
import com.example.travelbuddyapp.ui.theme.SaralaFont
import com.example.travelbuddyapp.viewmodel.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val viewModel: AuthViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Editar Perfil",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontFamily = SaralaFont,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Título
            Text(
                text = "¡Actualiza tu",
                fontFamily = SaralaFont,
                fontSize = 24.sp,
                color = Color.Black
            )
            Text(
                text = "Información!",
                fontFamily = SaralaFont,
                fontSize = 24.sp,
                color = Color(0xFF5B3EC8)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Foto de perfil (sin icono de cámara como pediste)
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color(0xFFE6E6E6), shape = CircleShape)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campos de texto
            ProfileInput(label = "Nombre", value = "", icon = Icons.Default.Person)
            ProfileInput(label = "Apellido", value = "", icon = Icons.Default.Person)
            ProfileInput(label = "Fecha de nacimiento", value = "DD/MM/YY", icon = Icons.Default.DateRange)
            ProfileInput(label = "Teléfono", value = "", placeholder = "", icon = Icons.Default.Phone)

            Spacer(modifier = Modifier.height(8.dp))

            // Botón guardar
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA181FA)
                ),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "Guardar",
                    fontFamily = SaralaFont,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ProfileInput(
    label: String,
    value: String,
    placeholder: String = "",
    icon: ImageVector
) {
    var text by remember { mutableStateOf(value) }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        label = {
            Text(text = label, color = Color(0xFF5B3EC8), fontFamily = SaralaFont)
        },
        placeholder = {
            if (placeholder.isNotEmpty()) {
                Text(text = placeholder, fontFamily = SaralaFont)
            }
        },
        leadingIcon = {
            Icon(imageVector = icon, contentDescription = label, tint = Color.Gray)
        },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color(0xFFE0E0E0),
            focusedBorderColor = Color(0xFF5B3EC8),
            cursorColor = Color(0xFF5B3EC8)
        ),
        singleLine = true
    )
}
