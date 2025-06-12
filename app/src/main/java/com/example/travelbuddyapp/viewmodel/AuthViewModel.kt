package com.example.travelbuddyapp.viewmodel
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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
    private val _userId = mutableStateOf<Int?>(null)
    val currentUserId: State<Int?> = _userId
    private val _registerFlag = mutableStateOf<Boolean>(false)
    val registerFlag: State<Boolean> = _registerFlag

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
            if(username.isNullOrEmpty()){
                println("Error obteniendo username.")
            }
            else{
                _currentUser.value = username
            }

        }
    }

    fun register(first_name:String, last_name:String, email:String, password:String, phone:String, location:String, birthDate: String){
        val roleId = "9e957475-6ab1-4bf8-9acc-2abae37cf58d"
       viewModelScope.launch(Dispatchers.IO){
            _registerFlag.value =
                authRepository.register(
                RegisterData(first_name, last_name, email, password, roleId),
                phone,
                birthDate,
                location
            )
        }
    }

    fun setRegisterFlag(state: Boolean){
        _registerFlag.value = state
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

    fun getUserId(){

        viewModelScope.launch(Dispatchers.IO) {
            val userIdStr = auxRepository.getUserId()
            if(userIdStr.isNullOrEmpty()){
                println("Error obteniendo userId")
            }
            val userId = userIdStr?.toIntOrNull()
            _userId.value = userId
        }

    }

    fun logout(){
        viewModelScope.launch(Dispatchers.IO){
            authRepository.clearDataStorage()
        }
    }
}

data class AuthState(
    var state:String = IDLE_AUTH_STATE
)

var AUTH_STATE = "AUTH"
var NO_AUTH_STATE = "NO_AUTH"
var IDLE_AUTH_STATE = "IDLE_AUTH"


