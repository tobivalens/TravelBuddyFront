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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.travelbuddyapp.resources.ui.screens.HomeScreen
import com.example.travelbuddyapp.resources.ui.screens.TravelItem
import com.example.travelbuddyapp.resources.ui.screens.ActivitiesScreen
import com.example.travelbuddyapp.resources.ui.screens.ActivityDetailScreen
import com.example.travelbuddyapp.resources.ui.screens.CreateActivityScreen
import com.example.travelbuddyapp.resources.ui.screens.CreateEvent
import com.example.travelbuddyapp.resources.ui.screens.EditActivityScreen
import com.example.travelbuddyapp.resources.ui.screens.EditEventScreen
import com.example.travelbuddyapp.resources.ui.screens.JoinEventScreen
import com.example.travelbuddyapp.resources.ui.screens.LoginScreen
import com.example.travelbuddyapp.resources.ui.screens.OptionAddScreen
import com.example.travelbuddyapp.resources.ui.screens.PantallaGastosUsuario
import com.example.travelbuddyapp.resources.ui.screens.RecoverPassword
import com.example.travelbuddyapp.resources.ui.screens.RegisterUserScreen
import com.example.travelbuddyapp.resources.ui.screens.UserProfile
import com.example.travelbuddyapp.resources.ui.screens.VisualizeEventScreenAdmin
import com.example.travelbuddyapp.viewmodel.AuthViewModel


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

    val viewModel: AuthViewModel = viewModel()
    val username by viewModel.currentUser

    LaunchedEffect(Unit) {
        viewModel.getUser()
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


        composable("editEvent/{eventId}",
            arguments = listOf(navArgument("eventId"){type = NavType.IntType})
        ){ backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
            EditEventScreen(eventId, navController)}


        //composable("gastos"){}

        composable(
            "VisualizeActivities/{eventId}",
                    arguments = listOf(
                        navArgument("eventId")
                        {type = NavType.IntType}
                    )
            ){
                backStackEntry ->
                val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
                ActivitiesScreen(
                    navController = navController,
                    eventId = eventId
                )
        }


        composable("VisualizeEvent/{eventId}",
            arguments = listOf(
                navArgument("eventId"){type = NavType.IntType})
        ){ backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
            VisualizeEventScreenAdmin(
                eventId = eventId,
                onBackClick = {navController.navigate("home")},
                onEditEvent = {navController.navigate("editEvent/$eventId")},
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

        /*
        composable("activities") {
            ActivitiesScreen(navController = navController)
        }

        */
        composable("createActivity/{eventId}",
            arguments = listOf(navArgument("eventId"){type = NavType.IntType}))
        { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId")?: return@composable

            CreateActivityScreen(eventId = eventId,
                onBack = { navController.popBackStack()})
        }

        composable("home"){ HomeScreen(
            navController,
            userName = username!!,
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
                //navController.navigate("splash")
            },
            onTravelClick = {},
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


        composable("actDetail/{actId}",
            arguments = listOf(navArgument("actId"){type =NavType.IntType})
        ) { backStackEntry ->
            val actId = backStackEntry.arguments?.getInt("actId")?: return@composable

            ActivityDetailScreen(
                actId,
                navController,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("editAct/{actId}",
            arguments = listOf(navArgument("actId"){type = NavType.IntType})
        ) {backStackEntry ->
            val actId = backStackEntry.arguments?.getInt("actId")?: return@composable

            EditActivityScreen(
                actId,
                onBack = { navController.popBackStack() }
            )
        }

        composable("optionalAdd"){ OptionAddScreen( navController, onHomeClick = {
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

        composable("userExpenses") {
            PantallaGastosUsuario(
                navController = navController,
                onBack = { navController.navigate("home") }
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



