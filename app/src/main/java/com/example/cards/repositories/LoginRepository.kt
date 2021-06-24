package com.example.cards.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.cards.constantes.Constantes
import com.example.cards.services.UserService
import com.example.cards.services.dto.UserDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class LoginRepository {
    var respuestaLogin = MutableLiveData<UserDto>()
    var isLogin = MutableLiveData<Boolean>()
    private fun getRetrofit(): UserService {
        return Retrofit.Builder()
                .baseUrl(Constantes.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(UserService::class.java)
    }

    fun getRespuestaLogin(email: String, password: String) {
        getRetrofit().LoginUser(email, password).enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                respuestaLogin.value = response.body()
                isLogin.value = true
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                Log.d("Login","Error en login response")
                isLogin.value = false
            }
        })
    }
}