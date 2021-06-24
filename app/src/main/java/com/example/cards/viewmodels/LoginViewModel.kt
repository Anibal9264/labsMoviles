package com.example.cards.viewmodels

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

}