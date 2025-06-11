package com.example.travelbuddyapp.resources.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.travelbuddyapp.ui.theme.SaralaFont
import com.example.travelbuddyapp.viewmodel.EventViewModel


@Composable
fun JoinEventScreen(
    navController: NavController,
    onHomeClick: () -> Unit,
    onAddClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val codeEvent = remember { mutableStateOf("") }
    val eventViewModel: EventViewModel = viewModel()

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(Color(0xFFA181FA))
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 16.dp, top = 32.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
            }
        },
        bottomBar = {
            HomeBottomBar(
                onHomeClick = onHomeClick,
                onAddClick = onAddClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F3F8))
                .padding(innerPadding)
        ) {
            Column {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(Color(0xFFA181FA)) // lila claro
                ) {
                    Spacer(modifier = Modifier.height(16.dp))


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


                        Text(
                            text = "Unirse a un Evento",
                            color = Color(0xFFA181FA),
                            fontFamily = SaralaFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 24.sp,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(bottom = 24.dp)
                        )



                        TextField(
                            value = codeEvent.value,
                            onValueChange = { codeEvent.value = it },
                            placeholder = {
                                Text(
                                    "Código",
                                    fontFamily = SaralaFont,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFFCBC7C7)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            shape = RoundedCornerShape(40.dp),

                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedContainerColor = Color(0xFFFFFFFB),
                                unfocusedContainerColor = Color(0xFFFFFFFB),
                                disabledContainerColor = Color(0xFFFFFFFB)
                            )

                        )



                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                eventViewModel.joinEvent(codeEvent.value)

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA181FA)),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = "Siguiente",
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