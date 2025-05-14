package com.example.travelbuddyapp.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddyapp.datasource.EventData
import com.example.travelbuddyapp.datasource.LoginData
import com.example.travelbuddyapp.datasource.RegisterData
import com.example.travelbuddyapp.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    val authRepository: AuthRepository = AuthRepository()
) : ViewModel(){

    var authState: MutableStateFlow<AuthState> = MutableStateFlow<AuthState>( AuthState() )

    fun login(email:String, pass:String) {

        viewModelScope.launch(Dispatchers.IO) {
            authRepository.login(
                LoginData(
                email, pass
            )
            )
            authState.value = AuthState(state = AUTH_STATE)
        }
    }

    fun register(first_name:String, last_name:String, email:String, password:String, phone:String, location:String, birthDate: String){
        val roleId = "9e957475-6ab1-4bf8-9acc-2abae37cf58d"
       viewModelScope.launch(Dispatchers.IO){
            authRepository.register(
                RegisterData(first_name, last_name, email, password, roleId),
                phone,
                birthDate,
                location
            )
        }
    }

    fun createEvent(eventName:String, description:String){
        viewModelScope.launch(Dispatchers.IO){
            authRepository.createEvent(
                eventName,
                description
            )
        }
    }

    fun getAuthStatus() {
        viewModelScope.launch (Dispatchers.IO){
            var accessToken = authRepository.getAccessToken()
            accessToken?.let {
                if(it.isEmpty()){
                    authState.value = AuthState(state = NO_AUTH_STATE)
                }else{
                    authState.value = AuthState(state = AUTH_STATE)
                }
            }
        }
    }

    fun getAllUsers() {
        viewModelScope.launch (Dispatchers.IO){
            authRepository.getAllUsers()
        }
    }

}

data class AuthState(
    var state:String = IDLE_AUTH_STATE
)

var AUTH_STATE = "AUTH"
var NO_AUTH_STATE = "NO_AUTH"
var IDLE_AUTH_STATE = "IDLE_AUTH"


