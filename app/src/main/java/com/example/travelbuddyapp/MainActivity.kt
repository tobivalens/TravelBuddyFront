package com.example.travelbuddyapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.shadow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelbuddyapp.datasource.local.LocalDataSourceProvider
import com.example.travelbuddyapp.ui.theme.SaralaFont
import com.example.travelbuddyapp.viewmodel.AuthViewModel
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.core.DataStore
import androidx.navigation.NavController
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.travelbuddyapp.viewmodel.AUTH_STATE
import androidx.compose.ui.platform.LocalContext
import com.example.travelbuddyapp.resources.ui.screens.HomeScreen
import com.example.travelbuddyapp.resources.ui.screens.TravelItem


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "AppVariables")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LocalDataSourceProvider.init(applicationContext.dataStore)
        enableEdgeToEdge()
        setContent {
            TravelBuddyAppTheme {
                AppNavigator()
            }
        }
    }
}


@Composable
fun AppNavigator() {
    val context = LocalContext.current
    val navController = rememberNavController()
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
        composable("registerUser"){RegisterUserScreen()}
        composable("recoverPassword"){ RecoverPassword() }
        composable("home"){ HomeScreen()}
        composable("profile"){ UserProfile(
            onHomeClick = {
                navController.navigate("home")
            }
        ) }
        composable("home"){ HomeScreen(
            userName = "Juan David Reyes",
            tabs = listOf("Todos", "Mis Viajes", "Otros"),
            travels = listOf(
                TravelItem(
                    id = "montaña",
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/e/e7/Everest_North_Face_toward_Base_Camp_Tibet_Luca_Galuzzi_2006.jpg",
                    title = "Viaje a montaña",
                    description = "Un gran viaje a la montaña"
                )
            ),
            selectedTab = 0,
            onTabSelected = { idx ->
                var selectedTab = idx
            },
            onSearchClick = {
                navController.navigate("splash")
            },
            onTravelClick = { item ->
                navController.navigate("travelDetail/${item.id}")
            },
            onHomeClick = {
                navController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            },
            onAddClick = {
                navController.navigate("createTravel")
            },
            onProfileClick = {
                navController.navigate("profile")
            }
        )}
    }
}

@Composable
fun RegisterUserScreen() {

    val viewModel: AuthViewModel = viewModel()
    viewModel.login("atkinsonvi2@gmail.com", "apps2025")
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val birthDate = remember { mutableStateOf("") }
    val location = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

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
                    TextField(
                        value = firstName.value,
                        onValueChange = {firstName.value = it},
                        placeholder = {
                            Text("Nombre",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7))},
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
                        onValueChange = {lastName.value = it},
                        placeholder = {
                            Text("Apellido",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7))},
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
                        onValueChange = {phone.value = it},
                        placeholder = {
                            Text("Telefono",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7))},
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
                        onValueChange = {location.value = it},
                        placeholder = {
                            Text("Ubicacion",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7))},
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
                        onValueChange = {birthDate.value = it},
                        placeholder = {
                            Text("Fecha de nacimiento (YYYY-MM-DD)",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7))},
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
                        onValueChange = {email.value = it},
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
                        value = password.value,
                        onValueChange = {password.value = it},
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
                        value = confirmPassword.value,
                        onValueChange = {confirmPassword.value = it},
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
fun LoginScreen(
    context: Context,
    onRegisterClick: () -> Unit,
    onForgetPassword: () -> Unit,
    onLoginSuccess: () -> Unit
) {

    val viewModel: AuthViewModel = viewModel() //
    val authState by viewModel.authState.collectAsState()

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(authState.state) {
        if (authState.state == AUTH_STATE) {
            onLoginSuccess()
        }
    }

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
            ){
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
                        value = email.value,
                        onValueChange = {email.value = it},
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
                        value = password.value,
                        onValueChange = {password.value = it},
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

                    Text(
                        text = "Olvidé mi contraseña",
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 12.dp)
                            .clickable {onForgetPassword() },
                        fontSize = 12.sp,
                        color = Color(0xFF9D7DF2) ,
                        fontFamily = SaralaFont,
                        fontWeight = FontWeight.SemiBold ,

                        )

                    // Botón Iniciar sesión
                    Button(
                        onClick = {
                            isLoading = true
                            viewModel.login(email.value, password.value)
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

@Composable
fun UserProfile(
    onHomeClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F3F8)) // fondo gris claro
    ) {
        Column {
            // Parte superior morada
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color(0xFFA181FA)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color(0xFFE0E0E0), shape = CircleShape)
                            .border(2.dp, Color.White, shape = CircleShape)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Daniel Escobar",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = SaralaFont,
                        color = Color.White
                    )
                }
            }

            // Caja de opciones

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-50).dp)
                    .padding(horizontal = 24.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(24.dp),
                        clip = true
                    )
                    .background(Color.White, shape = RoundedCornerShape(24.dp))
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                ) {
                    // Primer opción
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = Color(0xFFA181FA)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Editar Perfil",
                                fontSize = 16.sp,
                                fontFamily = SaralaFont,
                                color = Color.Black
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = null,
                            tint = Color(0xFFA181FA),
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color(0xFFA181FA))
                    Spacer(modifier = Modifier.height(8.dp))

                    // Segunda opción
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AttachMoney,
                                contentDescription = null,
                                tint = Color(0xFFA181FA)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Mis Gastos",
                                fontSize = 16.sp,
                                fontFamily = SaralaFont,
                                color = Color.Black
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = null,
                            tint = Color(0xFFA181FA),
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color(0xFFA181FA))
                    Spacer(modifier = Modifier.height(8.dp))

                    // Tercera opción
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = null,
                                tint = Color(0xFFA181FA)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Configuración",
                                fontSize = 16.sp,
                                fontFamily = SaralaFont,
                                color = Color.Black
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = null,
                            tint = Color(0xFFA181FA),
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color(0xFFA181FA))
                    Spacer(modifier = Modifier.height(8.dp))

                    // Cuarta opción
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = null,
                                tint = Color(0xFFA181FA)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Cerrar Sesión",
                                fontSize = 16.sp,
                                fontFamily = SaralaFont,
                                color = Color.Black
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = null,
                            tint = Color(0xFFA181FA),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // Barra de navegación inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color(0xFFA181FA))
                .height(60.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        onHomeClick()
                    }
                ) {
                    Icon(
                        imageVector      = Icons.Default.Home,
                        contentDescription = "Home",
                        tint             = Color.White
                    )
                }
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.White
                )
            }
        }
    }
}





