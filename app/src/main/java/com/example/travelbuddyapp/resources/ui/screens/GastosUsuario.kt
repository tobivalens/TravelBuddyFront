package com.example.travelbuddyapp.resources.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travelbuddyapp.resources.ui.components.BottomNavigationBar
import com.example.travelbuddyapp.resources.utils.formatMoney
import com.example.travelbuddyapp.ui.theme.CardBackground
import com.example.travelbuddyapp.ui.theme.PurpleHeader
import com.example.travelbuddyapp.ui.theme.Sarala
import com.example.travelbuddyapp.ui.theme.SoftText
import com.example.travelbuddyapp.viewmodel.ExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaGastosUsuario(
    userId: Int,
    navController: NavController,
) {

    val viewModel: ExpenseViewModel = viewModel()
    val userExpenses by viewModel.userExpenses
    val totalExpenses by viewModel.userTotal

    LaunchedEffect(userId) {
        viewModel.loadUserExpenses(userId)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 1) HEADER MORADO con botón de back
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(PurpleHeader)
                .padding(16.dp)
        ) {
            IconButton(onClick = {navController.popBackStack()}) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
            }
            Spacer(Modifier.width(8.dp))
            Text(
                "Mis Gastos",
                fontFamily = Sarala,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Sus gastos totales son:",
                    fontFamily = Sarala,
                    color = Color.White
                )
                Text(
                    "$ $totalExpenses",
                    fontFamily = Sarala,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Color.White
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        userExpenses.forEach { expense ->
            ExpenseItemUser(expenseId = expense.id_gasto, description = expense.descripcion, amount = "$ ${expense.monto}", debtorId = expense.deudor_id)
        }

        Spacer(Modifier.height(16.dp))

        Spacer(modifier = Modifier.weight(1f))

        // 5) BOTTOM BAR
        BottomNavigationBar(navController)
    }
}
