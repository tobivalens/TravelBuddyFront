package com.example.travelbuddyapp.viewmodel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddyapp.datasource.DTOS.*
import com.example.travelbuddyapp.repository.AuthRepository
import com.example.travelbuddyapp.repository.AuxRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    val authRepository: AuthRepository = AuthRepository(),
    val auxRepository: AuxRepository = AuxRepository()
) : ViewModel(){

    var authState: MutableStateFlow<AuthState> = MutableStateFlow<AuthState>( AuthState() )
    private val _currentUser = mutableStateOf<String?>(null)
    val currentUser: State<String?> = _currentUser

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

    fun getUser(){
        viewModelScope.launch {
            val username = auxRepository.getUsername()
            _currentUser.value = username
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

    fun getAuthStatus() {
        viewModelScope.launch (Dispatchers.IO){
            var accessToken = auxRepository.getAccessToken()
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
            auxRepository.getAllUsers()
        }
    }

    fun storeUserId(){

        viewModelScope.launch(Dispatchers.IO){
            auxRepository.storeCurrentUserId()
        }
    }
}

data class AuthState(
    var state:String = IDLE_AUTH_STATE
)

var AUTH_STATE = "AUTH"
var NO_AUTH_STATE = "NO_AUTH"
var IDLE_AUTH_STATE = "IDLE_AUTH"


