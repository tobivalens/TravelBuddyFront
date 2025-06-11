package com.example.travelbuddyapp.resources.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travelbuddyapp.resources.ui.components.BottomNavigationBar
import com.example.travelbuddyapp.ui.theme.SaralaFont
import com.example.travelbuddyapp.viewmodel.AUTH_STATE
import com.example.travelbuddyapp.viewmodel.AuthViewModel


@Composable
fun OptionAddScreen(
    navController: NavController,
    onHomeClick: () -> Unit,
    onAddClick: () -> Unit,
    onProfileClick: () -> Unit
) {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) {innerPadding->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F3F8)).padding(innerPadding)
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
                        text = "¡Es hora de iniciar ",
                        fontSize = 36.sp,
                        fontFamily = SaralaFont,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFFFFFFB),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 30.dp, top = 70.dp)
                    )
                    Text(
                        text = "la aventura ",
                        fontSize = 36.sp,
                        fontFamily = SaralaFont,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFFFFFFB),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 30.dp, top = 120.dp)
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
                        .background(
                            Color(0xFFF2F3F8),
                            shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Button(
                            onClick = {
                                navController.navigate("CreateEvent")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA181FA)),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = "Crear Evento",
                                fontFamily = SaralaFont,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFFFFB),
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                navController.navigate("joinEvent")

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA181FA)),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = "Unirse a Evento",
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
}