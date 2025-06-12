package com.example.travelbuddyapp.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddyapp.datasource.DTOS.EventResponse
import com.example.travelbuddyapp.repository.EventsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventViewModel(
    val eventRepository: EventsRepository = EventsRepository()
) : ViewModel() {

    private val _events = mutableStateOf<List<EventResponse>>(emptyList())
    val events: State<List<EventResponse>> = _events
    private val _ownedEvents = mutableStateOf<List<EventResponse>>(emptyList())
    val ownedEvents: State<List<EventResponse>> = _ownedEvents
    private val _partEvents = mutableStateOf<List<EventResponse>>(emptyList())
    val partEvents: State<List<EventResponse>> = _partEvents
    private val _currentEvent = mutableStateOf<EventResponse?>(null)
    val currentEvent: State<EventResponse?> = _currentEvent
    private val _participants = mutableStateOf<List<String>>(emptyList())
    val participants: State<List<String>> = _participants
    private val _createFlag = mutableStateOf<Boolean>(false)
    val createFlag: State<Boolean> = _createFlag
    private val _editFlag = mutableStateOf<Boolean>(false)
    val editFlag: State<Boolean> = _editFlag
    private val _joinFlag = mutableStateOf<Boolean>(false)
    val joinFlag: State<Boolean> = _joinFlag

    fun createEvent(eventName: String, description: String, startDate: String, endDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _createFlag.value =
                eventRepository.createEvent(
                eventName,
                description,
                startDate,
                endDate
            )
        }
    }

    fun editEvent(
        id: Int,
        newName: String,
        newDesc: String,
        newStartDate: String,
        newEndDate: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            _editFlag.value =
                eventRepository.editEvent(
                id,
                newName,
                newDesc,
                newStartDate,
                newEndDate
            )
        }
    }

    fun getAllEvents() {
        viewModelScope.launch(Dispatchers.IO) {

            val response = eventRepository.getAllEvents()
            if(response.isNullOrEmpty()){
                println("Error obteniendo todos los eventos.")
            }else{
                _events.value = response
            }


        }
    }

    fun getOwnedEvents(){
        viewModelScope.launch(Dispatchers.IO) {

            val response = eventRepository.getOwnedEvents()
            if(response.isNullOrEmpty()){
              println("Error obteniendo eventos administrados.")
            }else{
                _ownedEvents.value = response
            }
        }
    }

    fun getParticipatedEvents(){
        viewModelScope.launch(Dispatchers.IO) {

            val response = eventRepository.getParticipatedEvents()
            if(response.isNullOrEmpty()){
                println("Error obteniendo participantes.")
            }else{
                _partEvents.value = response
            }


        }
    }


    fun getEventById(id: Int) {
        viewModelScope.launch {
            val event = eventRepository.getEventById(id)
            if(event == null){
                println("Eror obteniendo evento por ID.")
            }
            _currentEvent.value = event
        }
    }

    fun deleteEvent(id: Int) {

        viewModelScope.launch {
            eventRepository.deleteEvent(id)
        }
    }

    fun joinEvent(unionCode: String) {

        viewModelScope.launch {
            _joinFlag.value = eventRepository.joinEvent(unionCode)
        }
    }

    fun setCreateFlag(status: Boolean) {
        _createFlag.value = status
    }

    fun setEditFlag(status: Boolean) {
        _editFlag.value = status
    }

    fun setJoinFlag(status: Boolean) {
        _joinFlag.value = status
    }

    fun loadParticipants(eventId: Int) {
        viewModelScope.launch {
            try {
                _participants.value = eventRepository.getParticipantsNames(eventId)
            } catch (e: Exception) {
                Log.e("Error", "Error: ${e.message}")
            }
        }
    }
}