package com.example.travelbuddyapp.resources.ui.screens

import androidx.benchmark.perfetto.Row
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelbuddyapp.ui.theme.SaralaFont
import com.example.travelbuddyapp.viewmodel.AuthViewModel
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController


@Composable
fun RegisterUserScreen(navController: NavController) {

    val scrollState = rememberScrollState()

    val viewModel: AuthViewModel = viewModel()
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val birthDate = remember { mutableStateOf("") }
    val location = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val registerFlag by viewModel.registerFlag

    if(registerFlag){
        AlertDialog(
            onDismissRequest = {viewModel.setRegisterFlag(false)},
            title = {Text("Exito")},
            text = { Text("Se ha registrado correctamente.")},
            confirmButton = {
                TextButton(onClick = {
                    viewModel.setRegisterFlag(false)
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color(0xFFA181FA))
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 16.dp, top = 32.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "¡Únete a nosotros! ",
                    fontSize = 36.sp,
                    fontFamily = SaralaFont,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFFFFFFB),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 40.dp, top = 100.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-42).dp)
                    .background(
                        Color(0xFFF2F3F8),
                        shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp)
                    )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Registrate",
                            color = Color(0xFFA181FA),
                            fontFamily = SaralaFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 24.sp,
                        )


                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = {
                                // Acción al presionar la cámara
                            }) {
                                Icon(
                                    imageVector = Icons.Default.PhotoCamera,
                                    contentDescription = "Tomar foto"
                                )
                            }
                        }
                    }
                    TextField(
                        value = firstName.value,
                        onValueChange = { firstName.value = it },
                        placeholder = {
                            Text(
                                "Nombre",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7)
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(40.dp),

                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color(0xFFFFFFFB),
                            unfocusedContainerColor = Color(0xFFFFFFFB),
                            disabledContainerColor = Color(0xFFFFFFFB)
                        )

                    )

                    TextField(
                        value = lastName.value,
                        onValueChange = { lastName.value = it },
                        placeholder = {
                            Text(
                                "Apellido",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7)
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(40.dp),

                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color(0xFFFFFFFB),
                            unfocusedContainerColor = Color(0xFFFFFFFB),
                            disabledContainerColor = Color(0xFFFFFFFB)
                        )

                    )

                    TextField(
                        value = phone.value,
                        onValueChange = { phone.value = it },
                        placeholder = {
                            Text(
                                "Telefono",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7)
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(40.dp),

                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color(0xFFFFFFFB),
                            unfocusedContainerColor = Color(0xFFFFFFFB),
                            disabledContainerColor = Color(0xFFFFFFFB)
                        )

                    )

                    TextField(
                        value = location.value,
                        onValueChange = { location.value = it },
                        placeholder = {
                            Text(
                                "Ubicacion",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7)
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.LocationOn, contentDescription = null)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(40.dp),

                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color(0xFFFFFFFB),
                            unfocusedContainerColor = Color(0xFFFFFFFB),
                            disabledContainerColor = Color(0xFFFFFFFB)
                        )

                    )

                    TextField(
                        value = birthDate.value,
                        onValueChange = { birthDate.value = it },
                        placeholder = {
                            Text(
                                "Nacimiento (YYYY-MM-DD)",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7)
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Cake, contentDescription = null)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(40.dp),

                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color(0xFFFFFFFB),
                            unfocusedContainerColor = Color(0xFFFFFFFB),
                            disabledContainerColor = Color(0xFFFFFFFB)
                        )

                    )

                    TextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        placeholder = {
                            Text(
                                "Email",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7)
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(40.dp),

                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color(0xFFFFFFFB),
                            unfocusedContainerColor = Color(0xFFFFFFFB),
                            disabledContainerColor = Color(0xFFFFFFFB)
                        )

                    )

                    val passwordVisible = remember { mutableStateOf(false) }

                    TextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        placeholder = {
                            Text(
                                "Contraseña",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7)
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null)
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisible.value = !passwordVisible.value
                            }) {
                                Icon(
                                    imageVector = if (passwordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (passwordVisible.value) "Ocultar contraseña" else "Mostrar contraseña"
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(40.dp),
                        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color(0xFFFFFFFB),
                            unfocusedContainerColor = Color(0xFFFFFFFB),
                            disabledContainerColor = Color(0xFFFFFFFB)
                        )
                    )


                    val confirmPasswordVisible = remember { mutableStateOf(false) }

                    TextField(
                        value = confirmPassword.value,
                        onValueChange = { confirmPassword.value = it },
                        placeholder = {
                            Text(
                                "Confirmar contraseña",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7)
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null)
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                confirmPasswordVisible.value = !confirmPasswordVisible.value
                            }) {
                                Icon(
                                    imageVector = if (confirmPasswordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (confirmPasswordVisible.value) "Ocultar contraseña" else "Mostrar contraseña"
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(40.dp),
                        visualTransformation = if (confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color(0xFFFFFFFB),
                            unfocusedContainerColor = Color(0xFFFFFFFB),
                            disabledContainerColor = Color(0xFFFFFFFB)
                        )
                    )


                    Button(
                        onClick = {
                            viewModel.register(
                                firstName.value,
                                lastName.value,
                                email.value,
                                password.value,
                                phone.value,
                                location.value,
                                birthDate.value
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA181FA)),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(
                            text = "Registrarse",
                            fontFamily = SaralaFont,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFFFFB),
                        )
                    }

                }
            }
        }
    }
}