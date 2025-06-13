package com.example.travelbuddyapp.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelbuddyapp.config.RetrofitConfig
import com.example.travelbuddyapp.datasource.DTOS.ActivityDTO
import com.example.travelbuddyapp.datasource.DTOS.ActivityData
import com.example.travelbuddyapp.datasource.local.FileDataSource
import com.example.travelbuddyapp.datasource.local.FileUpdateRequest
import com.example.travelbuddyapp.datasource.local.LocalDataSourceProvider
import com.example.travelbuddyapp.datasource.local.LocalDataStore
import com.example.travelbuddyapp.repository.ActivitiesRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ActivityViewModel(
    val activityRepository: ActivitiesRepository = ActivitiesRepository()
) : ViewModel(){

    private val _activities = mutableStateOf<List<ActivityDTO>>(emptyList())
    val activities: State<List<ActivityDTO>> = _activities
    private val _currentActivity = mutableStateOf<ActivityDTO?>(null)
    val currentActivity: State<ActivityDTO?> = _currentActivity
    private val _createFlag = mutableStateOf<Boolean>(false)
    val createFlag: State<Boolean> = _createFlag
    private val _editFlag = mutableStateOf<Boolean>(false)
    val editFlag: State<Boolean> = _editFlag

    fun loadActivities(eventId:Int){
        viewModelScope.launch(Dispatchers.IO){
            val list = activityRepository.loadActivities(eventId)
            _activities.value = list
        }
    }

    fun loadActivity(actId:Int){

        viewModelScope.launch(Dispatchers.IO){
            val act = activityRepository.loadActivity(actId)
            _currentActivity.value = act
        }
    }

    fun createActivity(eventId: Int, activityName:String,
                       description:String,
                       startDate: String, time: String,
                       location: String, image_id : String? ){




        val activityData = ActivityData(
            id_evento = eventId,
            nombre = activityName,
            descripcion = description,
            fecha_actividad = startDate,
            hora_actividad = time,
            ubicacion = location,
            id_imagen = image_id // null si no hay imagen
        )

        val json = Gson().toJson(activityData)
        Log.e("JSON_ACTIVIDAD", json)
        viewModelScope.launch(Dispatchers.IO){
            _createFlag.value =
                activityRepository.createActivity(
                eventId,
                activityName,
                description,
                startDate,
                time,
                location,
                    if (image_id.isNullOrBlank()) null else image_id
            )
        }
    }
    fun editActivity(id: Int, newName: String, newDesc: String, newDate: String, newTime:String, newLoc:String){
        viewModelScope.launch(Dispatchers.IO){
            _editFlag.value =
                activityRepository.editActivity(
                id,
                newName,
                newDesc,
                newDate,
                newTime,
                newLoc
            )
        }
    }

    fun setCreateFlag(status: Boolean) {
        _createFlag.value = status
    }

    fun setEditFlag(status: Boolean) {
        _editFlag.value = status
    }

    fun deleteActivity(id:Int){
        viewModelScope.launch(Dispatchers.IO){
            activityRepository.deleteActivity(id)
        }
    }
}



///////////////


class ImagesViewModel(
    val imagenActividad: ImagesRepository = ImagesRepository()
) : ViewModel() {

    var urlImage = MutableStateFlow(
        "https://raw.githubusercontent.com/Domiciano/AppMoviles251/refs/heads/main/res/images/Lab4Cover.png"
    )

    // ✅ ID real que necesitas para asociar la imagen en Directus
    private val _imageId = MutableStateFlow<String?>(null)
    val imageId: StateFlow<String?> = _imageId

    fun uploadImage(image: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            imagenActividad.uploadImage(image)?.let { imageId ->
                _imageId.value = imageId  // ✅ guarda el id
                urlImage.value = " https://7c13-2800-e2-4b80-1136-59bf-5cb6-323c-27b6.ngrok-free.app/assets/$imageId"
            }
        }
    }
}

class ImagesRepository(
    val fileDataSource: FileDataSource = RetrofitConfig.directusRetrofit.create(
        FileDataSource::class.java
    )
) {
    suspend fun uploadImage(image: Uri):String? {
        //Uri -> Multipart.Body
        val mp = MultipartProvider.get().prepareMultipartFromUri(image);
        val localDataSource: LocalDataStore = LocalDataSourceProvider.get()
        val token = localDataSource.load("accesstoken").first()
        val response = fileDataSource.uploadFile("Bearer $token", mp)
        response.body()?.let {
            Log.e(">>>", it.data.id)
            val response = fileDataSource.updateFileMetadata(
                "Bearer $token",
                it.data.id,
                FileUpdateRequest(
                    it.data.id,
                    it.data.id
                )
            )
            Log.e(">>>", response.code().toString())
            if(response.code() == 200){
                return it.data.id
            }

        }
        return null
    }

}
