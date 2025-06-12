package com.example.travelbuddyapp.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddyapp.repository.ImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    val profileRepo: ImagesRepository = ImagesRepository()
) : ViewModel() {
    var urlImage =
        MutableStateFlow("https://raw.githubusercontent.com/Domiciano/AppMoviles251/refs/heads/main/res/images/Lab4Cover.png")

    fun uploadImage(image: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepo.uploadImage(image)?.let { imageId ->
                urlImage.value = "http://10.0.2.2:8055/assets/$imageId"
            }
        }
    }
}
