package com.example.cards.repositories

import android.util.Log
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import com.example.cards.constantes.Constantes
import com.example.cards.services.UserService
import com.example.cards.services.dto.UserDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RegistroRepository {

    val respuestaRegistro = MutableLiveData<UserDto>()
    val isregistro = MutableLiveData<Boolean>()

    private fun getRetrofit(): UserService {
        return Retrofit.Builder()
                .baseUrl(Constantes.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(UserService::class.java)
    }

    fun getRespuestaRegistro(nombre: String, apellidos: String, id: String, fechanac: String,
                             email: String, password:String) {
        getRetrofit().RegistroUser(nombre, apellidos, id, fechanac, email, password).enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                respuestaRegistro.value = response.body()
                isregistro.value = true
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                Log.d("Registro","Error en Registro response")
                isregistro.value = false
            }
        })
    }
}