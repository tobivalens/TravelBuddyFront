package com.example.travelbuddyapp.resources.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travelbuddyapp.resources.ui.components.BottomNavigationBar
import com.example.travelbuddyapp.ui.theme.SaralaFont
import com.example.travelbuddyapp.viewmodel.AuthViewModel

@Composable
fun UserProfile(
    navController: NavController,
) {

    val viewModel: AuthViewModel = viewModel()
    val userName by viewModel.currentUser

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                            text = userName!!,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = SaralaFont,
                            color = Color.White
                        )
                    }
                }


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
                        modifier = Modifier.padding(vertical = 12.dp)
                    ) {

                        OptionRow(
                            icon = Icons.Default.Edit,
                            text = "Editar Perfil",
                            onClick = { navController.navigate("editProfile") }
                        )
                        Divider(color = Color(0xFFA181FA))


                        OptionRow(
                            icon = Icons.Default.AttachMoney,
                            text = "Mis Gastos",
                            onClick = { navController.navigate("userExpenses") }
                        )
                        Divider(color = Color(0xFFA181FA))


                        OptionRow(
                            icon = Icons.Default.Settings,
                            text = "Configuración",
                            onClick = { navController.navigate("profile") }
                        )

                        HorizontalDivider(color = Color(0xFFA181FA))

                        OptionRow(
                            icon = Icons.Default.ExitToApp,
                            text = "Cerrar Sesión",
                            onClick = {
                                viewModel.logout()
                                navController.navigate("loginScreen")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OptionRow(icon: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,

    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFFA181FA)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
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
