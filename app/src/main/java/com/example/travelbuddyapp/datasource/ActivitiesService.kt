package com.example.travelbuddyapp.datasource
import com.example.travelbuddyapp.datasource.DTOS.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ActivitiesService {
    @POST("/items/actividad")
    suspend fun createActivity(@Header("Authorization") authorization: String, @Body activityData: ActivityData): Response<EventResponse>

    @PATCH("/items/actividad/{id}")
    suspend fun editActivity(@Header("Authorization") authorization: String, @Path("id") id: Int, @Body editActivityData: EditActivityData)

    @GET("/items/actividad")
    suspend fun getActivities(@Header("Authorization") authorization: String,@Query("filter[id_evento][_eq]") idEvento: Int): Response<GetActivitiesData>

    @GET("/items/actividad/{id}")
    suspend fun getActivity(@Header("Authorization") authorization: String, @Path("id") id:Int): Response<SingleActivityData>

    @DELETE
    suspend fun deleteActivities(@Header("Authorization") authorization: String, @Path("id") id:Int)


}