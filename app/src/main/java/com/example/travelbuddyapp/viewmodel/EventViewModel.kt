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
    private val _currentEvent = mutableStateOf<EventResponse?>(null)
    val currentEvent: State<EventResponse?> = _currentEvent
    private val _participants = mutableStateOf<List<String>>(emptyList())
    val participants: State<List<String>> = _participants

    fun createEvent(eventName: String, description: String, startDate: String, endDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
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
            try {
                val response = eventRepository.getAllEvents()

                // Si el repositorio devuelve null, usamos datos mock
                _events.value = response ?: listOf(
                    EventResponse(
                        id_evento = 1,
                        nombre = "Evento de prueba",
                        descripcion = "Este es un evento de ejemplo para pruebas",
                        fecha_inicio = "2025-06-15",
                        fecha_fin = "2025-06-17",
                        codigo_union = "ABC123",
                        id_administrador = 999
                    ),
                    EventResponse(
                        id_evento = 2,
                        nombre = "Segundo evento",
                        descripcion = "Otro evento de prueba",
                        fecha_inicio = "2025-07-01",
                        fecha_fin = "2025-07-03",
                        codigo_union = "XYZ789",
                        id_administrador = 999
                    )
                )

                Log.e("events success", _events.value.toString())
            } catch (e: Exception) {
                Log.e("events error", e.message ?: "Error desconocido")

                // Fallback en caso de excepción
                _events.value = listOf(
                    EventResponse(
                        id_evento = 1,
                        nombre = "Evento Mock por excepción",
                        descripcion = "No se pudo acceder a la BD",
                        fecha_inicio = "2025-06-10",
                        fecha_fin = "2025-06-11",
                        codigo_union = "FAILSAFE1",
                        id_administrador = 0
                    )
                )
            }
        }
    }


    fun getEventById(id: Int) {
        viewModelScope.launch {
            val event = eventRepository.getEventById(id)
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
            eventRepository.joinEvent(unionCode)
        }
    }

    fun loadParticipants(eventId: Int) {
        viewModelScope.launch {
            try {
                _participants.value = eventRepository.getParticipantsNames(eventId)
            } catch (e: Exception) {
                Log.e("ViewModel", "Error: ${e.message}")
            }
        }
    }
}