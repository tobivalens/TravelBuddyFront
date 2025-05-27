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

    fun createActivity(eventId: Int, activityName:String, description:String, startDate: String, time: String, location: String ){
        viewModelScope.launch(Dispatchers.IO){
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

    fun deleteActivity(id:Int){
        viewModelScope.launch(Dispatchers.IO){
            activityRepository.deleteActivity(id)
        }
    }
}