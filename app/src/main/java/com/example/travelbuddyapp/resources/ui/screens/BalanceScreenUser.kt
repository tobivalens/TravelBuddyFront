package com.example.travelbuddyapp.resources.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.travelbuddyapp.resources.ui.components.BottomNavigationBar
import com.example.travelbuddyapp.ui.theme.SaralaFont
import com.example.travelbuddyapp.viewmodel.EventViewModel
import com.example.travelbuddyapp.viewmodel.ExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceScreenUser(eventId: Int,
                      onBackClick: () -> Unit,
                      onHomeClick: () -> Unit,
                      onAddClick: () -> Unit,
                      onProfileClick: () -> Unit,
                      navController: NavController) {

    val tabs = listOf("Evento", "Gastos", "Actividades")
    val purpleColor = Color(0xFFA181FA)
    val whiteBackground = Color(0xFFFFFFFF)
    val textColor = Color(0xFF52545B)
    val eventViewModel: EventViewModel = viewModel()
    val event by eventViewModel.currentEvent
    val expenseViewModel: ExpenseViewModel = viewModel()
    val expenses by expenseViewModel.expenses
    val total by expenseViewModel.total
    val totalUser by expenseViewModel.userEventTotal

    LaunchedEffect(eventId) {
        eventViewModel.getEventById(eventId)
        expenseViewModel.loadExpenses(eventId)
        expenseViewModel.loadUserExpensesInEvent(eventId)
    }


    val eventTitle = event?.nombre?: "Sin nombre"


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val selectedTab = when {
        currentRoute?.startsWith("VisualizeEvent") == true -> 0
        currentRoute == "gastosUser" -> 1
        currentRoute?.startsWith("VisualizeActivities") == true -> 2
        else -> 0 // default
    }

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
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController
            )
        },
        containerColor = whiteBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(Modifier.height(12.dp))

            // Tabs
            CustomTabBar(
                tabs = tabs,
                selectedTab = selectedTab,
                onTabSelected = { index ->
                    when (tabs[index]) {
                        "Evento" -> navController.navigate("VisualizeEvent/${eventId}")
                        "Gastos" -> navController.navigate("gastosUser/${eventId}")
                        "Actividades" -> navController.navigate("VisualizeActivities/${eventId}")
                    }
                }
            )

            Spacer(Modifier.height(32.dp))
            
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "¡Los gastos suman!",
                    fontSize = 32.sp,
                    fontFamily = SaralaFont,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "$ $total",
                    fontSize = 32.sp,
                    fontFamily = SaralaFont,
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }



            Spacer(Modifier.height(8.dp))

            Text(
                "Usted debe",
                color = purpleColor,
                fontWeight = FontWeight.Bold,
                fontFamily = SaralaFont
            )
            Text("$ $totalUser", color = textColor, fontFamily = SaralaFont)
            
            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Historial de Gastos", color = purpleColor, fontWeight = FontWeight.Bold, fontFamily = SaralaFont)
            }

            Spacer(Modifier.height(16.dp))

            expenses.forEach { expense ->
                ExpenseItemUser(expenseId = expense.id_gasto, description = expense.descripcion, amount = "$ ${expense.monto}", debtorId = expense.deudor_id)
            }
        }
    }
}
