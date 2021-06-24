package com.example.cards.services
import android.text.Editable
import retrofit2.Call
import com.example.cards.services.dto.UserDto
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService{
    @GET("?p=Login")
    fun LoginUser(
            @Query("email") email: String,
            @Query("password") pass: String
    ): Call<UserDto>

    @GET("?p=Registro")
    fun RegistroUser(
        @Query("nombre") nombre: String,
        @Query("apellidos") apellidos: String,
        @Query("id") id: String,
        @Query("fechanac") fechanac: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<UserDto>
}
