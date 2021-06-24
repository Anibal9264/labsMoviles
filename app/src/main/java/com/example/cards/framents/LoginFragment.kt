package com.example.cards.framents

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.cards.R
import androidx.lifecycle.Observer
import com.example.cards.constantes.Constantes
import com.example.cards.services.UserService
import com.example.cards.services.dto.UserDto
import com.example.cards.viewmodels.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.*
import java.util.Base64.*


class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var sharedPreferences: SharedPreferences? = null
    private val model: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
        if(sharedPreferences?.getBoolean("log",false) == true){
            view.findNavController().
            navigate(R.id.perfilFragment)
        }

        // verificar email
        val email =  view.findViewById<EditText>(R.id.email)
        val password =  view.findViewById<EditText>(R.id.password)
        val iniciar =  view.findViewById<Button>(R.id.iniciar)
        val registrese = view.findViewById<Button>(R.id.registrese)
        iniciar.isEnabled = false
        email.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(model.isEmailValid(email.text) && model.isPassValid(password.text) ){  iniciar.isEnabled = true}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(model.isEmailValid(email.text)){
                    email.getBackground().mutate().setColorFilter(getResources().getColor(R.color.correct), PorterDuff.Mode.SRC_ATOP);
                 }else{
                    email.getBackground().mutate().setColorFilter(getResources().getColor(R.color.incorrect), PorterDuff.Mode.SRC_ATOP);
                    email.setError("Formato Incorrecto")
                    iniciar.isEnabled = false
                }
            }

        })

        password.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(model.isEmailValid(email.text) && model.isPassValid(password.text) ){  iniciar.isEnabled = true}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(model.isPassValid(password.text) && model.isEmailValid(email.text)){
                    password.getBackground().mutate().setColorFilter(getResources().getColor(R.color.correct), PorterDuff.Mode.SRC_ATOP);
                    iniciar.isEnabled = true
                }else{
                    password.getBackground().mutate().setColorFilter(getResources().getColor(R.color.incorrect), PorterDuff.Mode.SRC_ATOP);
                    password.setError("No cumple con los requisitos minimos")
                    iniciar.isEnabled = false
                }
            }

        })


        iniciar.setOnClickListener{
            verificar(email,password,view)
        }

        registrese.setOnClickListener {
            view.findNavController().
            navigate(R.id.registroFragment)
        }


    }

    fun verificar(email: EditText, password: EditText, view: View) {

        var emailV = email.text.toString()
        var passwordV = password.text.toString()
        model.Login(emailV, passwordV)
        model.login.observe(viewLifecycleOwner, Observer {

            sharedPreferences = activity?.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
            var editor = sharedPreferences?.edit()
            var nombre = it.nombreC
            var fechaN = it.fecha_nacimiento
            var email = it.email
            var foto = it.foto
            var pais = it.pais
            editor?.putString("nombre", nombre)
            editor?.putString("fechaN", fechaN)
            editor?.putString("email", email)
            editor?.putString("foto", foto)
            editor?.putString("pais", pais)
            editor?.putBoolean("log", true)
            editor?.apply()
            view.findNavController().navigate(R.id.tourFragment)

        })
        model.isLog.observe(viewLifecycleOwner, Observer {
            if (it == false) {
                password.getBackground().mutate().setColorFilter(getResources().getColor(R.color.incorrect), PorterDuff.Mode.SRC_ATOP);
                email.getBackground().mutate().setColorFilter(getResources().getColor(R.color.incorrect), PorterDuff.Mode.SRC_ATOP);
                password.setError("Usuario o contraseña incorrecta")
                email.setError("Usuario o contraseña incorrecta")
            }
        })
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

}