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

    suspend fun loadActivity(actId: Int): ActivityDTO?{

        val token = auxRepository.getAccessToken()
        val response = activitiesService.getActivity("Bearer $token", actId)

        return response.body()?.data

    }

    suspend fun createActivity(id_evento: Int, activityName: String, activityDescription: String, startDate: String, time: String, location: String, id_imagen:String?): Boolean{
        val token = auxRepository.getAccessToken()
        val response =
            activitiesService.createActivity("Bearer $token", ActivityData(
                id_evento,
                activityName,
                activityDescription,
                startDate,
                time,
                location,
                id_imagen
            )
            )

        return response.isSuccessful

    }
    suspend fun editActivity(id: Int, newName: String, newDesc: String, newDate:String, newTime:String, newLoc:String): Boolean{
        val token = auxRepository.getAccessToken()
        val response =
            activitiesService.editActivity("Bearer $token",
                id,
                EditActivityData(newName, newDesc, newDate, newTime, newLoc)
            )

        return response.isSuccessful
    }

    suspend fun deleteActivity(id: Int){
        val token = auxRepository.getAccessToken()
        activitiesService.deleteActivities("Bearer $token", id)
    }
}
