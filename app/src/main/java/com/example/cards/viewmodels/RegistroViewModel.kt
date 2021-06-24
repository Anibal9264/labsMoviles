package com.example.cards.viewmodels

import android.text.Editable
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cards.repositories.LoginRepository
import com.example.cards.repositories.RegistroRepository
import com.example.cards.services.dto.UserDto

class RegistroViewModel : ViewModel() {

    var registro = MutableLiveData<UserDto>()
    var isreg = MutableLiveData<Boolean>()
    var respuestaVMRegistro= RegistroRepository()

    init{
        registro = respuestaVMRegistro.respuestaRegistro
        isreg = respuestaVMRegistro.isregistro
    }

    fun Registro(nombre: String, apellidos: String, id: String, fechanac: String,
                 email: String, password:String){
        respuestaVMRegistro.getRespuestaRegistro(nombre, apellidos, id, fechanac, email, password)
    }

    //mascara para email
    fun isEmailValid(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    //mascara para registro
    fun isPassValid(password: Editable): Boolean {
        val pattern = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}\$".toRegex()
        val matches = pattern.matches(password)
        if (matches)return true
        return false
    }
}