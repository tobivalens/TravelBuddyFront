package com.example.travelbuddyapp.resources.ui.screens


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travelbuddyapp.ui.theme.SaralaFont
import com.example.travelbuddyapp.viewmodel.ExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpensesScreen(
    expenseId: Int,
    onBackClick: () -> Unit,
    navController: NavController
) {

    val viewModel: ExpenseViewModel = viewModel()
    val purpleColor = Color(0xFFA181FA)
    val backgroundColor = Color(0xFFFFFFFB)
    val fieldColor = Color.White
    val textColor = Color(0xFF52545B)
    val textColorPurple = Color(0xFFA181FA)
    val buttonWhiteText = Color(0xFFFFFFFB)
    val innerGrayColor = Color(0xFFCBC7C7)
    val expense by viewModel.currentExpense
    val editFlag by viewModel.editFlag

    LaunchedEffect(expenseId) {
        viewModel.loadExpenseById(expenseId)
    }

    var title by remember{ mutableStateOf("")}
    var amount by remember{ mutableStateOf("") }
    var debtorId by remember { mutableStateOf("") }

    LaunchedEffect(expense) {
        expense?.let{
            title = it.descripcion
            amount = it.monto.toString()
            debtorId = it.deudor_id.toString()
        }
    }

    if(editFlag){
        AlertDialog(
            onDismissRequest = {viewModel.setEditFlag(false)},
            title = {Text("Exito")},
            text = { Text("El gasto fue editado correctamente.")},
            confirmButton = {
                TextButton(onClick = {
                    viewModel.setEditFlag(false)
                    navController.popBackStack()
                }) {
                    Text("Aceptar.")
                }
            }
        )
    }

    val fieldShape = RoundedCornerShape(24.dp)

    Scaffold(
        topBar = {
            TopAppBarComponent(eventTitle = "Editar un gasto", onBackClick)
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text("¡Hora de re-ajustar", fontSize = 32.sp, fontFamily = SaralaFont, color = textColor)
            Text("finanzas!", fontSize = 32.sp, fontFamily = SaralaFont, color = textColor)

            Spacer(modifier = Modifier.height(32.dp))

            // Campo Nombre
            Text("Nombre", color = textColorPurple, fontFamily = SaralaFont, fontSize = 18.sp)
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = {
                    Text(
                        text = "Ingrese el nombre del gasto",
                        color = innerGrayColor
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Nombre",
                        tint = innerGrayColor
                    )
                },
                textStyle = TextStyle(color = innerGrayColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .shadow(6.dp, shape = fieldShape),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = fieldColor,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = purpleColor
                ),
                shape = fieldShape
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Deudor", color = textColorPurple, fontFamily = SaralaFont, fontSize = 18.sp)
            OutlinedTextField(
                value = debtorId,
                onValueChange = { debtorId = it },
                placeholder = {
                    Text(
                        text = "Ingrese el ID del deudor",
                        color = innerGrayColor
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Nombre",
                        tint = innerGrayColor
                    )
                },
                textStyle = TextStyle(color = innerGrayColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .shadow(6.dp, shape = fieldShape),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = fieldColor,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = purpleColor
                ),
                shape = fieldShape
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo Valor del gasto
            Text(
                "Valor del gasto",
                color = textColorPurple,
                fontFamily = SaralaFont,
                fontSize = 18.sp
            )
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                placeholder = {
                    Text(
                        text = "Ingrese el valor del gasto",
                        color = innerGrayColor
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AttachMoney,
                        contentDescription = "Valor",
                        tint = innerGrayColor
                    )
                },
                textStyle = TextStyle(color = innerGrayColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .shadow(6.dp, shape = fieldShape),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = fieldColor,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = purpleColor
                ),
                shape = fieldShape
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val amountDouble = amount.toDoubleOrNull() ?: 0.0
                    if (title.isNotBlank() && amountDouble > 0) {
                        viewModel.editExpense(expenseId, debtorId.toInt(), amountDouble, title)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = purpleColor),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Editar", color = buttonWhiteText, fontFamily = SaralaFont, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.deleteExpense(expenseId)
                    navController.popBackStack()
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = purpleColor),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Eliminar", color = buttonWhiteText, fontFamily = SaralaFont, fontSize = 18.sp)
            }
        }
    }
}