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
import com.example.cards.R
import com.example.cards.services.UserService
import com.example.cards.services.dto.UserDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var BASE_URL: String = "http://f0e25895c084.ngrok.io/Tours/"
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // verificar email
        val email =  view.findViewById<EditText>(R.id.email)
        val password =  view.findViewById<EditText>(R.id.password)
        val iniciar =  view.findViewById<Button>(R.id.iniciar)
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
                    iniciar.isEnabled = false
                }
            }

        })


        iniciar.setOnClickListener{
            verificar(email,password)

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



    fun verificar(email: EditText, password: EditText) {
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
            }

            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {

            }
        })
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

}