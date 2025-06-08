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
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.travelbuddyapp.ui.theme.SaralaFont


@Composable
fun BalanceScreen(generalExpenses: Double,personalExpense: Double,travelName: String,onBackClick: () -> Unit,
                  onHomeClick: () -> Unit,
                  onAddClick: () -> Unit,
                  onProfileClick: () -> Unit) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Evento", "Gastos", "Actividades")
    val purpleColor = Color(0xFFA181FA)
    val whiteBackground = Color(0xFFFFFFFF)
    val textColor = Color(0xFF52545B)
    val generalExpensesString= "$ "+generalExpenses
    val personalExpensesString= "$ "+personalExpense


    Scaffold(
        topBar = {
            //TopAppBarComponent(eventTitle = travelName,onBackClick)

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
                    generalExpensesString,
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
            Text(personalExpensesString, color = textColor, fontFamily = SaralaFont)

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Historial de Gastos", color = purpleColor, fontWeight = FontWeight.Bold, fontFamily = SaralaFont)
                Button(
                    onClick = { /* Navegar a añadir gasto */ },
                    colors = ButtonDefaults.buttonColors(containerColor = purpleColor),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text("Añadir Gasto", color = Color.White, fontFamily = SaralaFont, fontWeight =  FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(16.dp))
            //pruebas quemadas
            ExpenseItem("Hospedaje", "$30,000")
            ExpenseItem("Comida", "$10,000")
            ExpenseItem("Pasajes", "$6,097")
            ExpenseItem("Chimbo", "$6,097")
        }
    }
}

@Composable
fun ExpenseItem(name: String, amount: String) {
    val lightGray = Color(0xFFCBC7C7)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.AttachMoney, contentDescription = null, tint = lightGray)
            Spacer(modifier = Modifier.width(8.dp))
            Text(name, fontSize = 16.sp, color = lightGray, fontFamily = SaralaFont)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(amount, fontSize = 16.sp, color = lightGray,fontFamily = SaralaFont)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(Icons.Default.MoreVert, contentDescription = null, tint = lightGray)
        }
    }
    HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
}
