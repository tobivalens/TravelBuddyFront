package com.example.travelbuddyapp.repository

import com.example.travelbuddyapp.config.RetrofitConfig
import com.example.travelbuddyapp.datasource.ActivitiesService
import com.example.travelbuddyapp.datasource.DTOS.ActivityDTO
import com.example.travelbuddyapp.datasource.DTOS.ActivityData
import com.example.travelbuddyapp.datasource.DTOS.EditActivityData

class ActivitiesRepository(
    val activitiesService: ActivitiesService = RetrofitConfig.directusRetrofit.create(ActivitiesService::class.java),
    val auxRepository: AuxRepository = AuxRepository()
) {

    suspend fun loadActivities(eventId: Int):List<ActivityDTO>{
        val token = auxRepository.getAccessToken()
        val response = activitiesService.getActivities("Bearer $token", eventId)
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        }
        throw Exception("Error cargando evento: ${response.code()} ${response.message()}")
    }

    suspend fun createActivity(activityName: String, activityDescription: String, id_Evento: Int){
        val token = auxRepository.getAccessToken()
        activitiesService.createActivity("Bearer $token", ActivityData(
            activityName,
            activityDescription,
            id_Evento
        )
        )
    }
    suspend fun editActivity(id: Int, newName: String, newDesc: String){
        val token = auxRepository.getAccessToken()
        activitiesService.editActivity(
            "Bearer $token", id,
            EditActivityData(newName, newDesc)
        )
    }
}