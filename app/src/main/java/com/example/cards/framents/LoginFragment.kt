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
import androidx.navigation.findNavController
import com.example.cards.R
import com.example.cards.TourFragment
import com.example.cards.services.UserService
import com.example.cards.services.dto.UserDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var BASE_URL: String = "http://f9f9cf21afd3.ngrok.io/Tours/"
    private var sharedPreferences: SharedPreferences? = null

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
                if(isEmailValid(email.text) ||isPassValid(password.text) ){  iniciar.isEnabled = true}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(isEmailValid(email.text)){
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
                if(isEmailValid(email.text) ||isPassValid(password.text) ){  iniciar.isEnabled = true}
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(isPassValid(password.text)){
                    password.getBackground().mutate().setColorFilter(getResources().getColor(R.color.correct), PorterDuff.Mode.SRC_ATOP);

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


    /*AlertDialog.Builder(requireContext()).setTitle("valido")
    .setMessage(email.text)
    .setPositiveButton(android.R.string.ok) {_,_->}
    .setIcon(android.R.drawable.ic_dialog_alert).show()*/

    fun isEmailValid(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPassValid(pass: Editable): Boolean {
        val pattern = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}\$".toRegex()
        val matches = pattern.matches(pass)
        if (matches)return true
        return false
    }



    fun verificar(email: EditText, password: EditText, view: View) {
        val service = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(UserService::class.java)
        service.LoginUser(email.text,password.text)
        service.LoginUser(email.text, password.text).enqueue(object : Callback<UserDto> {

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
                password.getBackground().mutate().setColorFilter(getResources().getColor(R.color.incorrect), PorterDuff.Mode.SRC_ATOP);
                email.getBackground().mutate().setColorFilter(getResources().getColor(R.color.incorrect), PorterDuff.Mode.SRC_ATOP);
                password.setError("Usuario o contraseña incorrecta")
                email.setError("")
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


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

}