package com.example.travelbuddyapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelbuddyapp.datasource.DTOS.ActivityDTO
import com.example.travelbuddyapp.repository.ActivitiesRepository
import kotlinx.coroutines.Dispatchers
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
            if(list.isEmpty()){
                println("Error cargando actividades por id")
            }
            _activities.value = list
        }
    }

    fun loadActivity(actId:Int){

        viewModelScope.launch(Dispatchers.IO){
            val act = activityRepository.loadActivity(actId)
            if(act == null){
                println("Error cargando actividad por id")
            }
            _currentActivity.value = act
        }
    }

    fun createActivity(eventId: Int, activityName:String, description:String, startDate: String, time: String, location: String ){
        viewModelScope.launch(Dispatchers.IO){
            _createFlag.value =
                activityRepository.createActivity(
                eventId,
                activityName,
                description,
                startDate,
                time,
                location
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