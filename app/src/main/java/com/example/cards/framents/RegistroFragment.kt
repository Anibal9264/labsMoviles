package com.example.cards.framents

import android.content.Context
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.example.cards.R
import com.example.cards.services.UserService
import com.example.cards.services.dto.UserDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RegistroFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var BASE_URL: String = "http://369fee821fae.ngrok.io/Tours/"
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
        if (sharedPreferences?.getBoolean("log", false) == true) {
            view.findNavController().navigate(R.id.perfilFragment)
        }

        //todas las variables
        val nombre =  view.findViewById<EditText>(R.id.nombre)
        val apellidos =  view.findViewById<EditText>(R.id.apellidos)
        val id =  view.findViewById<EditText>(R.id.id)
        val fechanac =  view.findViewById<EditText>(R.id.fechanac)
        val email =  view.findViewById<EditText>(R.id.email)
        val password =  view.findViewById<EditText>(R.id.password)
        val registrar = view.findViewById<Button>(R.id.registrar)

        //validar textos
        email.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if (isEmailValid(email.text)) {
                    email.getBackground().mutate().setColorFilter(getResources().getColor(R.color.correct), PorterDuff.Mode.SRC_ATOP);
                } else {
                    email.getBackground().mutate().setColorFilter(getResources().getColor(R.color.incorrect), PorterDuff.Mode.SRC_ATOP);
                    email.setError("Formato Incorrecto")
                }
            }
        })

        password.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if(isPassValid(password.text)){
                    password.getBackground().mutate().setColorFilter(getResources().getColor(R.color.correct), PorterDuff.Mode.SRC_ATOP);
                }else{
                    password.getBackground().mutate().setColorFilter(getResources().getColor(R.color.incorrect), PorterDuff.Mode.SRC_ATOP);
                    password.setError("No cumple con los requisitos minimos")
                }
            }

        })

        registrar.setOnClickListener{
            verificar(nombre, apellidos, id, fechanac, email, password, view)
        }

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

    fun verificar(nombre:EditText, apellidos: EditText, id: EditText, fechanac: EditText,
                  email: EditText, password: EditText, view: View) {

        val service = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(UserService::class.java)
        service.RegistroUser(nombre.text, apellidos.text, id.text, fechanac.text, email.text, password.text)
        service.RegistroUser(nombre.text, apellidos.text, id.text, fechanac.text, email.text, password.text).enqueue(object : Callback<UserDto> {

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                nombre.getBackground().mutate().setColorFilter(getResources().getColor(R.color.incorrect), PorterDuff.Mode.SRC_ATOP);
                nombre.setError("Error al registrar")
            }

            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                sharedPreferences = activity?.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
                var editor = sharedPreferences?.edit()
                var nombre = response.body()?.nombreC
                var fechaN = response.body()?.fecha_nacimiento
                var email = response.body()?.email
                var foto = response.body()?.foto
                var pais = response.body()?.pais
                editor?.putString("nombre", nombre)
                editor?.putString("fechaN", fechaN)
                editor?.putString("email", email)
                editor?.putString("foto", foto)
                editor?.putString("pais", pais)
                editor?.putBoolean("log", true)
                editor?.apply()
                view.findNavController().
                navigate(R.id.tourFragment)
            }
        })
    }
}