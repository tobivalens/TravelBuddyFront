package com.example.travelbuddyapp.repository

import android.net.Uri
import android.util.Log
import com.example.travelbuddyapp.config.RetrofitConfig
import com.example.travelbuddyapp.datasource.local.FileUpdateRequest
import com.example.travelbuddyapp.datasource.local.LocalDataSourceProvider
import com.example.travelbuddyapp.datasource.local.LocalDataStore
import com.example.travelbuddyapp.datasource.local.FileDataSource
import kotlinx.coroutines.flow.first



class ImagesRepository(
    val fileDataSource: FileDataSource = RetrofitConfig.directusRetrofit.create(
        FileDataSource::class.java
    )
) {
    suspend fun uploadImage(image: Uri):String? {
        //Uri -> Multipart.Body
        val mp = MultipartProvider.get().prepareMultipartFromUri(image);
        val localDataSource: LocalDataStore = LocalDataSourceProvider.get()
        val token = localDataSource.load("accesstoken").first()
        val response = fileDataSource.uploadFile("Bearer $token", mp)
        response.body()?.let {
            Log.e(">>>", it.data.id)
            val response = fileDataSource.updateFileMetadata(
                "Bearer $token",
                it.data.id,
                FileUpdateRequest(
                    it.data.id,
                    it.data.id
                )
            )
            Log.e(">>>", response.code().toString())
            if(response.code() == 200){
                return it.data.id
            }

        }
        return null
    }

}

