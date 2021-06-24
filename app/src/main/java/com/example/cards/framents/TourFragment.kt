package com.example.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cards.constantes.Constantes
import com.example.cards.services.TourService
import com.example.cards.services.dto.TourDto
import com.example.cards.viewmodels.TourViewmodel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class TourFragment : Fragment() {
    private val model: TourViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_tour, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val desdeF = view.findViewById<TextView>(R.id.desdeF)
        val hastaF = view.findViewById<TextView>(R.id.hastaF)
        val findtext = view.findViewById<TextView>(R.id.findtext)
        val bBuscar = view.findViewById<Button>(R.id.buscarB)

        var list_recycler_viewT = view.findViewById<RecyclerView>(R.id.RecycleviewOfTour)
        model.getTours("", "", "",)
        model.Tours.observe(viewLifecycleOwner, Observer {
            list_recycler_viewT.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = ToursListAdapter(it)
            }
        })
       bBuscar.setOnClickListener{
           model.getTours(findtext.text.toString(), desdeF.text.toString(), hastaF.text.toString())
        }

    }

    companion object {
        fun newInstance(): TourFragment = TourFragment()
    }

}