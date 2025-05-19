package com.example.travelbuddyapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.travelbuddyapp.ui.theme.TravelBuddyAppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import kotlinx.coroutines.delay
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.travelbuddyapp.datasource.local.LocalDataSourceProvider
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.core.DataStore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.travelbuddyapp.resources.ui.screens.HomeScreen
import com.example.travelbuddyapp.resources.ui.screens.TravelItem
import com.example.travelbuddyapp.datasource.Activity
import com.example.travelbuddyapp.resources.ui.screens.ActivityDetailScreen
import com.example.travelbuddyapp.resources.ui.screens.CreateEvent
import com.example.travelbuddyapp.resources.ui.screens.CreateEventScreen
import com.example.travelbuddyapp.resources.ui.screens.EditActivityScreen
import com.example.travelbuddyapp.resources.ui.screens.EditEventScreen
import com.example.travelbuddyapp.resources.ui.screens.JoinEventScreen
import com.example.travelbuddyapp.resources.ui.screens.LoginScreen
import com.example.travelbuddyapp.resources.ui.screens.OptionAddScreen
import com.example.travelbuddyapp.resources.ui.screens.RecoverPassword
import com.example.travelbuddyapp.resources.ui.screens.RegisterUserScreen
import com.example.travelbuddyapp.resources.ui.screens.UserProfile
import com.example.travelbuddyapp.resources.ui.screens.VisualizeEventScreenAdmin


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
    var activity by remember{
        mutableStateOf(
            Activity(
                id = "1",
                title = "Cabaña de skiing",
                description = "Reserva confirmada en nuestra acogedora cabaña con acceso directo a las pistas de ski.",
                date = "27 de julio de 2025",
                time = "10:00 AM",
                location = "El Albergue de las Nieves Skiing Chalet",
                imageUrl = "https://images.unsplash.com/photo-1586378742040-2e4c89f5ef7e"
            )
        )
    }

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
        composable("editEvent"){ EditEventScreen(navController)}
        composable("gastos"){}
        composable("VisualizeActivity"){

        }
        composable("VisualizeEvent"){
            VisualizeEventScreenAdmin(
                onBackClick = {navController.navigate("home")},
                onEditEvent = {navController.navigate("editEvent")},
                navController = navController
            )}
        composable("profile"){ UserProfile(
            onHomeClick = {
                navController.navigate("home")
            },

            onAddClick = {
                navController.navigate("optionalAdd")
            },

            onProfileClick = {navController.navigate("userProfile") }
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
                navController.navigate("VisualizeEvent")
            },
            onHomeClick = {
                navController.navigate("home") {
                    popUpTo("home") { inclusive = true }
                }
            },
            onAddClick = {
                navController.navigate("optionalAdd")
            },
            onProfileClick = {
                navController.navigate("profile")
            }
        )}
        composable("createEvent"){CreateEvent(
            navController,

            onHomeClick = {
            navController.navigate("home") },
            onAddClick = {
                navController.navigate("optionalAdd")
            },

            onProfileClick = {navController.navigate("userProfile") }
        )}
        composable("editEvent") { EditEventScreen(navController) }
        composable("detail") {
            ActivityDetailScreen(
                activity = activity,
                onEditClick = { navController.navigate("edit") }
            )
        }

        composable("edit") {
            EditActivityScreen(
                activity = activity,
                onSave = {
                    activity = it
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable("optionalAdd"){ OptionAddScreen( navController,onHomeClick = {
            navController.navigate("home")
        },

            onAddClick = {
                navController.navigate("optionalAdd")
            },

            onProfileClick = {navController.navigate("userProfile") })}

        composable("joinEvent"){
            JoinEventScreen(
                navController,onHomeClick = {
                    navController.navigate("home") },
                onAddClick = {
                    navController.navigate("optionalAdd")
                },

                onProfileClick = {navController.navigate("userProfile") }
            )
        }

    }


}


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


/**
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
                VisualizeEventScreenAdmin(navController)
            }

            composable("1") {
                // arreglar las rutas cuando esten completas

            }
        }
    }
}

**/

