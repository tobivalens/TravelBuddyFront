package com.example.travelbuddyapp.datasource.DTOS

data class Activity(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val location: String,
    val imageUrl: String
)


data class Activities(
    val id: String,
    val title: String,
    val date: String,
    val imageUrl: String? = null
)