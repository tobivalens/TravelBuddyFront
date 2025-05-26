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

    fun createActivity(activityName:String, description:String, eventId: Int){
        viewModelScope.launch(Dispatchers.IO){
            activityRepository.createActivity(
                activityName,
                description,
                eventId
            )
        }
    }
    fun editActivity(id: Int, newName: String, newDesc: String){
        viewModelScope.launch(Dispatchers.IO){
            activityRepository.editActivity(
                id,
                newName,
                newDesc
            )
        }
    }

    fun deleteActivity(id:Int){
        viewModelScope.launch(Dispatchers.IO){
            activityRepository.deleteActivity(id)
        }
    }
}