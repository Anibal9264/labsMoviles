package com.example.cards.viewmodels

import android.text.Editable
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cards.repositories.LoginRepository
import com.example.cards.services.dto.UserDto

class LoginViewModel : ViewModel() {
    var login = MutableLiveData<UserDto>()
    var isLog = MutableLiveData<Boolean>()
    var respuestaVMLogin = LoginRepository()

    init{
        login = respuestaVMLogin.respuestaLogin
        isLog = respuestaVMLogin.isLogin
    }

    fun Login(email: String, password: String){
        respuestaVMLogin.getRespuestaLogin(email,password)
    }

    fun isEmailValid(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPassValid(pass: Editable): Boolean {
        val pattern = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}\$".toRegex()
        val matches = pattern.matches(pass)
        if (matches)return true
        return false
    }

}