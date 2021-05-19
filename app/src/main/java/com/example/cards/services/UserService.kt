package com.example.cards.services
import android.text.Editable
import retrofit2.Call
import com.example.cards.services.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService{
    @GET("?p=Login")
    fun LoginUser(
            @Query("email") email: Editable,
            @Query("password") pass: Editable
    ): Call<UserDto>

    @POST("?p=Registro")
    fun RegistroUser(
            @Query("nombre") nombre: Editable,
            @Query("apellidos") apellidos: Editable,
            @Query("id") id: Editable,
            @Query("fechanac") fechanac: Editable,
            @Query("email") email: Editable,
            @Query("password") password: Editable
    ): Call<UserDto>
}
