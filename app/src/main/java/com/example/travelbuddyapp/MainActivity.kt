package com.example.travelbuddyapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.travelbuddyapp.ui.theme.TravelBuddyAppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import kotlinx.coroutines.delay
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.travelbuddyapp.ui.theme.SaralaFont

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelBuddyAppTheme {
                AppNavigator()
            }
        }
    }
}


@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    val context = LocalContext.current // Obtener el contexto aquí

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("loginScreen") {
            LoginScreen(
                context = context,
                onRegisterClick = { navController.navigate("registerUser") },
                onForgetPassword = { navController.navigate("recoverPassword") },
                onLoginSuccess = { navController.navigate("home") }
            )
        }
        composable("registerUser"){ RegisterUserScreen() }
        composable("recoverPassword"){ RecoverPassword() }
        composable("home") { HomeScreen() }
    }
}


@Composable
fun RegisterUserScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F3F8)) // fondo gris claro
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color(0xFFA181FA)) // lila claro
            ) {
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
                    .weight(1f)
                    .offset(y = (-42).dp)
                    .shadow(
                        elevation = 32.dp,
                        shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp),
                        clip = true
                    )
                    .background(Color(0xFFF2F3F8), shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Registrate",
                        color = Color(0xFFA181FA),
                        fontFamily = SaralaFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 24.dp)
                    )

                    // Campo Email
                    TextField(
                        value = "",
                        onValueChange = {},
                        placeholder = {
                            Text("Email",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7))},
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

                    TextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Contraseña",
                            fontFamily = SaralaFont,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFFCBC7C7)) },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null)
                        },
                        trailingIcon = {
                            Icon(Icons.Default.Visibility, contentDescription = null)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(40.dp),
                        visualTransformation = PasswordVisualTransformation(),
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
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Confirmar contraseña",
                            fontFamily = SaralaFont,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFFCBC7C7)) },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null)
                        },
                        trailingIcon = {
                            Icon(Icons.Default.Visibility, contentDescription = null)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(40.dp),
                        visualTransformation = PasswordVisualTransformation(),
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
                        onClick = {},
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

    }}


@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(true) {
        delay(3000) // 3 segundos
        navController.navigate("loginScreen") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp)
            )
        }
    }
}

@Composable
fun LoginScreen(context: Context, onRegisterClick: () -> Unit, onForgetPassword: () -> Unit, onLoginSuccess: () -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    // Variables de estado para los campos de texto
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F3F8)) // fondo gris claro
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color(0xFFA181FA)) // lila claro
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "¡Comienza a viajar ",
                    fontSize = 36.sp,
                    fontFamily = SaralaFont,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFFFFFFB),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 30.dp, top = 70.dp)
                )
                Text(
                    text = "con ",
                    fontSize = 36.sp,
                    fontFamily = SaralaFont,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFFFFFFB),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 30.dp, top = 120.dp)
                )
                Text(
                    text = "Travel Buddy!",
                    fontSize = 40.sp,
                    fontFamily = SaralaFont,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFFFFB),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 100.dp, top = 115.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .offset(y = (-42).dp)
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp),
                        clip = true
                    )
                    .background(Color(0xFFF2F3F8), shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Iniciar Sesión",
                        color = Color(0xFFA181FA),
                        fontFamily = SaralaFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 24.dp)
                    )

                    // Campo Email
                    TextField(
                        value = email,
                        onValueChange = { email = it },  // Actualiza el estado del email
                        placeholder = {
                            Text("Email",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7))
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                            .onFocusChanged { state ->
                                if (state.isFocused) {
                                    keyboardController?.show() // Muestra el teclado cuando el campo recibe el enfoque
                                }
                            },
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

                    // Campo Contraseña
                    TextField(
                        value = password,
                        onValueChange = { password = it },  // Actualiza el estado de la contraseña
                        placeholder = { Text("Contraseña",
                            fontFamily = SaralaFont,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFFCBC7C7)) },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null)
                        },
                        trailingIcon = {
                            Icon(Icons.Default.Visibility, contentDescription = null)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { state ->
                                if (state.isFocused) {
                                    keyboardController?.show() // Muestra el teclado cuando el campo recibe el enfoque
                                }
                            },
                        shape = RoundedCornerShape(40.dp),
                        visualTransformation = PasswordVisualTransformation(),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color(0xFFFFFFFB),
                            unfocusedContainerColor = Color(0xFFFFFFFB),
                            disabledContainerColor = Color(0xFFFFFFFB)
                        )
                    )

                    Text(
                        text = "Olvidé mi contraseña",
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 12.dp)
                            .clickable { onForgetPassword() },
                        fontSize = 12.sp,
                        color = Color(0xFF9D7DF2),
                        fontFamily = SaralaFont,
                        fontWeight = FontWeight.SemiBold,
                    )

                    // Botón Iniciar sesión
                    Button(
                        onClick = {
                            val email = email // Obtén el valor del correo
                            val password = password // Obtén el valor de la contraseña

                            // Simular llamada al servidor
                            val fakeAuthToken = "Bearer abcdef12345"

                            // Guardar el token
                            saveAuthToken(context, fakeAuthToken)

                            // Navegar al Home
                            onLoginSuccess()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA181FA)),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(
                            text = "Iniciar Sesión",
                            fontFamily = SaralaFont,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFFFFB),
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Texto registrarse
                    Row {
                        Text(
                            text = "¿No tienes cuenta? ",
                            color = Color(0xFFCBC7C7),
                            fontSize = 12.sp,
                            fontFamily = SaralaFont,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { onRegisterClick() }
                        )
                        Text(
                            text = "Regístrate",
                            color = Color(0xFF9D7DF2),
                            fontSize = 12.sp,
                            fontFamily = SaralaFont,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { onRegisterClick() }
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun RecoverPassword(){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F3F8)) // fondo gris claro
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color(0xFFA181FA)) // lila claro
            ) {
                Spacer(modifier = Modifier.height(16.dp))

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .offset(y = (-42).dp)
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp),
                        clip = true
                    )
                    .background(Color(0xFFF2F3F8), shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {



                    Text(
                        text = " Recuperar contraseña     ",
                        fontSize = 28.sp,
                        fontFamily = SaralaFont,
                        fontWeight = FontWeight.Normal,
                        color =  Color(0xFFA181FA),
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Start)


                    )

                    Spacer(modifier = Modifier.height(8.dp))


                    Text(
                        text = "Escribe el correo electrónico asociado a tu cuenta para que te enviemos un código de verificación",
                        fontSize = 12.sp,
                        fontFamily = SaralaFont,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFA8A8A8)

                    )

                    Spacer(modifier = Modifier.height(40.dp))
                    TextField(
                        value = "",
                        onValueChange = {},
                        placeholder = {
                            Text("Email",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7))},
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

                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA181FA)),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(
                            text = "Confirmar Correo",
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


@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F3F8)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Bienvenido a TravelBuddy!",
            fontSize = 24.sp,
            color = Color(0xFFA181FA),
            fontFamily = SaralaFont,
            fontWeight = FontWeight.Bold
        )
    }
}

fun saveAuthToken(context: Context, token: String) {
    val sharedPref = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("auth_token", token)
        apply()
    }
}





