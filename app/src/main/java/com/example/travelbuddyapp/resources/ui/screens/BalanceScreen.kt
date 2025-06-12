package com.example.travelbuddyapp.resources.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.example.travelbuddyapp.ui.theme.SaralaFont
import com.example.travelbuddyapp.viewmodel.EventViewModel
import com.example.travelbuddyapp.viewmodel.ExpenseViewModel


@Composable
fun BalanceScreen(eventId: Int,
                  onBackClick: () -> Unit,
                  onHomeClick: () -> Unit,
                  onAddClick: () -> Unit,
                  onProfileClick: () -> Unit,
                  navController: NavController){

    var selectedTab by remember { mutableStateOf(0) }
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



    Scaffold(
        topBar = {
            TopAppBarComponent(eventTitle = eventTitle,onBackClick)

        },
        bottomBar = {
            HomeBottomBar(
                onHomeClick = onHomeClick,
                onAddClick = onAddClick,
                onProfileClick = onProfileClick,

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
            Spacer(Modifier.height(16.dp))

            //barra superior
            CustomTabBar(
                tabs = tabs,
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
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
                Button(
                    onClick = { navController.navigate("addExpense/${eventId}") },
                    colors = ButtonDefaults.buttonColors(containerColor = purpleColor),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text("Añadir Gasto", color = Color.White, fontFamily = SaralaFont, fontWeight =  FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(16.dp))

            expenses.forEach { expense ->
                Log.e("ID_GASTO", expense.id_gasto.toString())
                ExpenseItem(expenseId = expense.id_gasto, description = expense.descripcion, amount = "$ ${expense.monto}", debtorId = expense.deudor_id, navController)
            }

        }
    }
}

@Composable
fun ExpenseItem(
    expenseId: Int,
    description: String,
    amount: String,
    debtorId: Int,
    navController: NavController
) {
    val lightGray = Color(0xFFCBC7C7)
    Log.e("ExpenseID - ExpenseItem ", expenseId.toString())
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("editExpense/${expenseId}") }
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AttachMoney, contentDescription = null, tint = lightGray)
                Spacer(modifier = Modifier.width(8.dp))
                Text(description, fontSize = 16.sp, color = lightGray, fontFamily = SaralaFont)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(amount, fontSize = 16.sp, color = lightGray, fontFamily = SaralaFont)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.MoreVert, contentDescription = null, tint = lightGray)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "Asignado al participante con ID = $debtorId",
            fontSize = 14.sp,
            color = lightGray,
            fontFamily = SaralaFont
        )
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
    }
}

