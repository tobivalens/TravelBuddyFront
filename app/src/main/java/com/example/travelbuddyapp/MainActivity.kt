package com.example.travelbuddyapp
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import com.example.travelbuddyapp.viewmodel.AUTH_STATE
import androidx.compose.ui.platform.LocalContext
import com.example.travelbuddyapp.resources.ui.screens.AppBottomNavigationBar
import com.example.travelbuddyapp.resources.ui.screens.CustomTabBar
import com.example.travelbuddyapp.resources.ui.screens.HomeScreen
import com.example.travelbuddyapp.resources.ui.screens.TravelItem
import java.text.SimpleDateFormat
import java.util.Locale


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
                onLoginSuccess = { navController.navigate("createEvent") }
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
        composable("createEvent"){CreateEventScreen()}
        composable("editEvent") { EditEventScreen(navController) }
    }
}




@Composable
fun RegisterUserScreen() {

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
                        .padding(40.dp).verticalScroll(scrollState),
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
fun CreateEventScreen() {

    val viewModel: AuthViewModel = viewModel()
    val eventName = remember { mutableStateOf("") }
    val eventDescription = remember { mutableStateOf("") }

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
                    text = "¡Únete a nosotros!",
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
                        text = "Crea un evento",
                        color = Color(0xFFA181FA),
                        fontFamily = SaralaFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 24.dp)
                    )
                    TextField(
                        value = eventName.value,
                        onValueChange = {eventName.value = it},
                        placeholder = {
                            Text("Nombre",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7))},
                        leadingIcon = {
                            Icon(Icons.Default.Event, contentDescription = null)
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
                        value = eventDescription.value,
                        onValueChange = {eventDescription.value = it},
                        placeholder = {
                            Text("Descripcion",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFCBC7C7))},
                        leadingIcon = {
                            Icon(Icons.Default.Description, contentDescription = null)
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
                        onClick = {
                            viewModel.createEvent(
                                eventName.value,
                                eventDescription.value
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

@Composable
fun EditEventScreen(navController: NavController) {
    var title by remember { mutableStateOf("Viaje a la montaña") }
    var description by remember { mutableStateOf("Disfruta de una experiencia única entre paisajes...") }
    var startDate by remember { mutableStateOf("Julio 04, 2025") }
    var endDate by remember { mutableStateOf("Julio 30, 2025") }
    var photo by remember { mutableStateOf("Montaña.jpg") }

    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    val formatter = SimpleDateFormat("MMMM dd, yyyy", Locale("es", "ES"))

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
                Text("Viaje a la montaña!", fontSize = 30.sp, fontWeight = FontWeight.Bold, fontFamily = SaralaFont)

                Spacer(modifier = Modifier.height(16.dp))

                LabeledField("Nombre", title, Icons.Default.Edit) { title = it }
                LabeledField("Descripción", description, Icons.Default.Description) { description = it }

                DateField("Día de inicio", startDate) { showStartDatePicker = true }
                DateField("Día de finalización", endDate) { showEndDatePicker = true }

                LabeledField("Foto", photo, Icons.Default.Image) { photo = it }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { /* navController.navigate("saveSuccess") */ },
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LabeledField(
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
private fun DateField(
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



// ---------------------- VISUALIZE SCREEN NORMAL-----------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisualizeEventScreen(
    eventTitle: String = "Viaje a la montaña",
    onBackClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Evento", "Gastos", "Actividades")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = eventTitle,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontFamily = SaralaFont,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
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
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(12.dp))

            // Tabs
            CustomTabBar(
                tabs = tabs,
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            Spacer(Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_logo), // reemplaza con tu imagen
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(16.dp))

            // la descripcion
            Text(
                text = "Disfruta de una experiencia única entre paisajes imponentes, aire puro y tranquilidad absoluta. Este viaje a las montañas te lleva a explorar senderos escondidos, miradores panorámicos y cascadas cristalinas...",
                color = Color(0xFFCBC7C7),
                fontSize = 12.sp,
                fontFamily = SaralaFont
            )

            Spacer(Modifier.height(24.dp))

            // Fechas estaticas por ahora
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Fecha de inicio", color = Color(0xFFA38AFB), fontWeight = FontWeight.Bold)
                    Text("Julio 04, 2025", color = Color.Gray)
                }
                Column {
                    Text("Fecha de finalización", color = Color(0xFFA38AFB), fontWeight = FontWeight.Bold)
                    Text("Julio 30, 2025", color = Color.Gray)
                }
            }

            Spacer(Modifier.height(24.dp))

            // lista users
            Text("Participantes", color = Color(0xFFA38AFB), fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(8.dp))

            val participantes = listOf("Jairo Vélez", "Sofía Puente", "Jordi Arroyo")
            participantes.forEach {
                ParticipantItem(name = it)
            }

            Spacer(Modifier.height(8.dp))

            //
            OutlinedButton(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, Color(0xFFA38AFB))
            ) {
                Text("+ Código de Evento", color = Color(0xFFA38AFB))
            }

            Spacer(Modifier.height(24.dp))
            //ARCHIVOS
            Text(
                text = "Archivos",
                color = Color(0xFF8C69FF),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Comparte aquí fotos, documentos o cualquier archivo importante del viaje. Todo lo que subas estará disponible para que todos los participantes lo vean y accedan fácilmente.",
                color = Color.Gray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.InsertDriveFile,
                        contentDescription = "Archivo",
                        tint = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "No hay archivos aún",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8C69FF)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text(text = "Subir", color = Color.White)
            }
        }
    }
}


//------------------------VISUALIZE SCREEN ADMIN-----------------------------




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisualizeEventScreenAdmin(
    eventTitle: String = "Viaje a la montaña",
    onBackClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Evento", "Gastos", "Actividades")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = eventTitle,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontFamily = SaralaFont,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },  actions = {
                    IconButton(onClick = { /* acción editar */ }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFFA181FA)
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(12.dp))

            // Tabs
            CustomTabBar (
                tabs = tabs,
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            Spacer(Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_logo), // reemplaza con tu imagen
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Disfruta de una experiencia única entre paisajes imponentes, aire puro y tranquilidad absoluta. Este viaje a las montañas te lleva a explorar senderos escondidos, miradores panorámicos y cascadas cristalinas...",
                color = Color(0xFFCBC7C7),
                fontSize = 12.sp,
                fontFamily = SaralaFont
            )

            Spacer(Modifier.height(24.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Fecha de inicio", color = Color(0xFFA38AFB), fontWeight = FontWeight.Bold)
                    Text("Julio 04, 2025", color = Color.Gray)
                }
                Column {
                    Text("Fecha de finalización", color = Color(0xFFA38AFB), fontWeight = FontWeight.Bold)
                    Text("Julio 30, 2025", color = Color.Gray)
                }
            }

            Spacer(Modifier.height(24.dp))

            Text("Participantes", color = Color(0xFFA38AFB), fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(8.dp))

            val participantes = listOf("Jairo Vélez", "Sofía Puente", "Jordi Arroyo")
            participantes.forEach {
                ParticipantItem(name = it)
            }

            Spacer(Modifier.height(8.dp))

            // Botón para añadir participante
            OutlinedButton(
                onClick = { /* Acción */ },
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, Color(0xFFA38AFB))
            ) {
                Text("+ Añadir participante", color = Color(0xFFA38AFB))
            }

            Spacer(Modifier.height(24.dp))
            //ARCHIVOS
            Text(
                text = "Archivos",
                color = Color(0xFF8C69FF),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Comparte aquí fotos, documentos o cualquier archivo importante del viaje. Todo lo que subas estará disponible para que todos los participantes lo vean y accedan fácilmente.",
                color = Color.Gray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.InsertDriveFile,
                        contentDescription = "Archivo",
                        tint = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "No hay archivos aún",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* Subir archivo */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8C69FF)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text(text = "Subir", color = Color.White)
            }
        }
    }
}

//-------participantes---------------------------------------


@Composable
fun ParticipantItem(name: String) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = Color(0xFF8C69FF),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = name,
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { /* Opciones */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Opciones",
                    tint = Color.Gray
                )
            }
        }

        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
    }
}



@Composable
fun HomeEventScreen() {

    val bottomController= rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomNavigationBar(bottomController, selectedItem = 0, onOptionClick = {})
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomController,
            startDestination = "visualizeEvent",
            modifier = Modifier.padding(innerPadding) 
        ) {
            composable("visualizeEvent") {
                VisualizeEventScreen()
            }
            //esto era para pruebas
            composable("recoverPassword") {
                VisualizeEventScreenAdmin()
            }

            composable("1") {
                // arreglar las rutas cuando esten completas

            }
        }
    }

}











