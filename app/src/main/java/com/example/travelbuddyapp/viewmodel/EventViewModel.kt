package com.example.travelbuddyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddyapp.repository.EventsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventViewModel(
    val eventRepository: EventsRepository = EventsRepository()
) : ViewModel(){

    fun createEvent(eventName:String, description:String){
        viewModelScope.launch(Dispatchers.IO){
            eventRepository.createEvent(
                eventName,
                description
            )
        }
    }
}