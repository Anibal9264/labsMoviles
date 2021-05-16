package com.example.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class Tour(
    val title: String,
    val puntuacion: Float,
    val lugar: String,
    val precios: Float,
    val catOpiniones: Int,
    val image: Int
)
class TourFragment : Fragment() {

    private val Tours = listOf(
        Tour("Playa Conchal",
            3.5F,
            "Santa Cruz, Guanacaste, Costa Rica",
            32.0F,
            134,
            R.drawable.conchal),
        Tour("Playa Hermosa",
            4.5F,
            "Sardinal, Carrillo, Guanacaste, Costa Rica",
            24.5F,
            341,
            R.drawable.hermosa),
        Tour("Monteverde",
            4F,
            "Monteverde, Puntarenas, Costa Rica",
            54F,
            123,
            R.drawable.monteverde),
        Tour("Parque Nacional Rincon de la vieja",
            3F,
            "Liberia, provincia: Guanacaste, Costa Rica",
            34F,
            1234,
            R.drawable.rincon),
        Tour("Laguna Arenal",
            2.5F,
            "Arenal, Costa Rica",
            23F,
            1242,
            R.drawable.laguna)

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_tour, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var list_recycler_viewT = view.findViewById<RecyclerView>(R.id.RecycleviewOfTour)
        var Recycler_View_Find = view.findViewById<RecyclerView>(R.id.RecycleviewOfFind)
        list_recycler_viewT.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ToursListAdapter(Tours)
        }
        Recycler_View_Find.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = FindAdapter()
        }

    }

    companion object {
        fun newInstance(): TourFragment = TourFragment()
    }
}