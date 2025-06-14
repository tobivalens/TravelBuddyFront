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
import com.example.travelbuddyapp.datasource.DTOS.EditExpenseData
import com.example.travelbuddyapp.resources.ui.screens.HomeScreen
import com.example.travelbuddyapp.resources.ui.screens.TravelItem
import com.example.travelbuddyapp.resources.ui.screens.ActivitiesScreen
import com.example.travelbuddyapp.resources.ui.screens.ActivitiesScreenParticipant
import com.example.travelbuddyapp.resources.ui.screens.ActivityDetailParticipant
import com.example.travelbuddyapp.resources.ui.screens.ActivityDetailScreen
import com.example.travelbuddyapp.resources.ui.screens.AddExpenseScreen
import com.example.travelbuddyapp.resources.ui.screens.BalanceScreen
import com.example.travelbuddyapp.resources.ui.screens.BalanceScreenUser
import com.example.travelbuddyapp.resources.ui.screens.CreateActivityScreen
import com.example.travelbuddyapp.resources.ui.screens.CreateEvent
import com.example.travelbuddyapp.resources.ui.screens.EditActivityScreen
import com.example.travelbuddyapp.resources.ui.screens.EditEventScreen
import com.example.travelbuddyapp.resources.ui.screens.EditExpensesScreen
import com.example.travelbuddyapp.resources.ui.screens.EditProfileScreen
import com.example.travelbuddyapp.resources.ui.screens.HomeScreenMT
import com.example.travelbuddyapp.resources.ui.screens.HomeScreenOT
import com.example.travelbuddyapp.resources.ui.screens.JoinEventScreen
import com.example.travelbuddyapp.resources.ui.screens.LoginScreen
import com.example.travelbuddyapp.resources.ui.screens.OptionAddScreen
import com.example.travelbuddyapp.resources.ui.screens.PantallaGastosUsuario
import com.example.travelbuddyapp.resources.ui.screens.RecoverPassword
import com.example.travelbuddyapp.resources.ui.screens.RegisterUserScreen
import com.example.travelbuddyapp.resources.ui.screens.UserProfile
import com.example.travelbuddyapp.resources.ui.screens.VisualizeEventScreen
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
        composable("registerUser") { RegisterUserScreen(navController) }
        composable("recoverPassword") { RecoverPassword(navController) }


        composable(
            "editEvent/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
            EditEventScreen(eventId, navController)
        }

        composable("gastosUser/{eventId}",
            arguments = listOf(navArgument("eventId"){type = NavType.IntType})
        ){ backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
            BalanceScreenUser(
                eventId,
                {navController.popBackStack()},
                { navController.navigate("home") },
                { navController.navigate("optionalAdd") },
                { navController.navigate("profile") },
                navController
        )}

        composable("gastos/{eventId}",
            arguments = listOf(navArgument("eventId"){type = NavType.IntType})
        ){ backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
            BalanceScreen(
                eventId,
                {navController.popBackStack()},
                { navController.navigate("home") },
                { navController.navigate("optionalAdd") },
                { navController.navigate("profile") },
                navController
            )}


        composable(
            "VisualizeActivitiesAdmin/{eventId}",
            arguments = listOf(
                navArgument("eventId")
                { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
            ActivitiesScreen(
                navController = navController,
                eventId = eventId
            )
        }

        composable(
            "VisualizeActivities/{eventId}",
            arguments = listOf(
                navArgument("eventId")
                { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
            ActivitiesScreenParticipant(
                navController = navController,
                eventId = eventId
            )
        }

        composable("addExpense/{eventId}",
            arguments = listOf(navArgument("eventId"){type = NavType.IntType})) {
            backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
            AddExpenseScreen(
                eventId = eventId,
                onBackClick = { navController.popBackStack() },
                navController
            )
        }
        composable("editExpense/{expId}",
            arguments = listOf(navArgument("expId"){type = NavType.IntType})){
            backStackEntry ->
            val expId = backStackEntry.arguments?.getInt("expId") ?: return@composable
            EditExpensesScreen(
                expenseId = expId,
                onBackClick = {navController.popBackStack()},
                navController
            )

        }
        composable("VisualizeEventAdmin/{eventId}",
            arguments = listOf(
                navArgument("eventId") { type = NavType.IntType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
            VisualizeEventScreenAdmin(
                eventId = eventId,
                onBackClick = {navController.navigate("home")},
                onEditEvent = {navController.navigate("editEvent/${eventId}")},
                navController = navController
            )
        }

        composable(
            "VisualizeEvent/{eventId}",
            arguments = listOf(
                navArgument("eventId") { type = NavType.IntType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
            VisualizeEventScreen(
                eventId = eventId,
                onBackClick = { navController.navigate("home") },
                navController = navController

            )
        }

        composable("profile") { UserProfile(navController, username!!) }

        composable("editProfile") {
            EditProfileScreen(
                navController = navController
            )
        }

        composable(
            "createActivity/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        )
        { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable

            CreateActivityScreen(eventId = eventId,
                onBack = { navController.popBackStack() },
                navController)
        }

        composable("home") {
            HomeScreen(
                navController,
                userName = username ?: "Invitado",
                tabs = listOf("Todos", "Mis Viajes", "Otros"),
                selectedTab = 0,
                onTabSelected = { idx ->
                    var selectedTab = idx
                    when(idx){
                        0 -> navController.navigate("home")
                        1 -> navController.navigate("homeMT")
                        2 -> navController.navigate("homeOT")
                    }
                },
                onSearchClick = {
                    navController.navigate("home")
                }
            )
        }
        composable("homeMT") {
            HomeScreenMT(
                navController,
                userName = username ?: "Invitado",
                tabs = listOf("Todos", "Mis Viajes", "Otros"),
                selectedTab = 0,
                onTabSelected = { idx ->
                    var selectedTab = idx
                    when(idx){
                        0 -> navController.navigate("home")
                        1 -> navController.navigate("homeMT")
                        2 -> navController.navigate("homeOT")
                    }
                },
                onSearchClick = {
                    navController.navigate("home")
                }
            )
        }
        composable("homeOT") {
            HomeScreenOT(
                navController,
                userName = username ?: "Invitado",
                tabs = listOf("Todos", "Mis Viajes", "Otros"),
                selectedTab = 0,
                onTabSelected = { idx ->
                    var selectedTab = idx
                    when(idx){
                        0 -> navController.navigate("home")
                        1 -> navController.navigate("homeMT")
                        2 -> navController.navigate("homeOT")
                    }
                },
                onSearchClick = {
                    navController.navigate("home")
                }
            )
        }
        composable("createEvent") {
            CreateEvent(
                navController,

                onHomeClick = {
                    navController.navigate("home")
                },
                onAddClick = {
                    navController.navigate("optionalAdd")
                },

                onProfileClick = { navController.navigate("userProfile") }
            )
        }


        composable(
            "actDetail/{actId}",
            arguments = listOf(navArgument("actId") { type = NavType.IntType })
        ) { backStackEntry ->
            val actId = backStackEntry.arguments?.getInt("actId") ?: return@composable

            ActivityDetailScreen(
                actId,
                navController,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            "actDetailUser/{actId}",
            arguments = listOf(navArgument("actId") { type = NavType.IntType })
        ) { backStackEntry ->
            val actId = backStackEntry.arguments?.getInt("actId") ?: return@composable
            ActivityDetailParticipant(
                actId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("userExpenses/{userId}",
        arguments = listOf(navArgument("userId"){type = NavType.IntType}))
        { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
            PantallaGastosUsuario(
                userId,
                navController = navController
            )
        }

        composable(
            "editAct/{actId}",
            arguments = listOf(navArgument("actId") { type = NavType.IntType })
        ) { backStackEntry ->
            val actId = backStackEntry.arguments?.getInt("actId") ?: return@composable

            EditActivityScreen(
                actId,
                onBack = { navController.popBackStack() },
                navController
            )
        }

        composable("optionalAdd") {
            OptionAddScreen(navController, onHomeClick = {
                navController.navigate("home")
            },

                onAddClick = {
                    navController.navigate("optionalAdd")
                },

                onProfileClick = { navController.navigate("userProfile") })
        }

        composable("joinEvent") {
            JoinEventScreen(
                navController, onHomeClick = {
                    navController.navigate("home")
                },
                onAddClick = {
                    navController.navigate("optionalAdd")
                },

                onProfileClick = { navController.navigate("userProfile") }
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



