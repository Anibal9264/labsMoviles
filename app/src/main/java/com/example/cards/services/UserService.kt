package com.example.cards.services
import android.text.Editable
import retrofit2.Call
import com.example.cards.services.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService{
    @GET("?p=Login")
    fun LoginUser(
            @Query("email") email: Editable,
            @Query("password") pass: Editable
    ): Call<UserDto>
}
