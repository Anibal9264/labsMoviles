package com.example.cards.framents

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.cards.R
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class PerfilFragment : Fragment() {

    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
        val nombre = view.findViewById<TextView>(R.id.name)
        nombre.text = sharedPreferences?.getString("nombre","Unknown")

        val fechaN = view.findViewById<TextView>(R.id.fecha_nacimiento)
        fechaN.text = sharedPreferences?.getString("fechaN","Unknown")

        val email = view.findViewById<TextView>(R.id.email)
        email.text = sharedPreferences?.getString("email","Unknown")

        val pais = view.findViewById<TextView>(R.id.pais)
        pais.text = sharedPreferences?.getString("pais","Unknown")

        /*val foto = view.findViewById<CircleImageView>(R.id.profile)
        val src = sharedPreferences?.getString("foto","Unknown")
        foto.setImageBitmap(src?.let { convertString64ToImage(it) })*/

        val salir = view.findViewById<Button>(R.id.salir)
        salir.setOnClickListener{
            sharedPreferences?.edit()?.clear()?.commit()
            view.findNavController().
            navigate(R.id.loginFragment)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

}