package com.example.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cards.services.TourService
import com.example.cards.services.dto.TourDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class TourFragment : Fragment() {
    private var BASE_URL: String = "http://369fee821fae.ngrok.io/Tours/"
    private val Tours = mutableListOf<TourDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_tour, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var Recycler_View_Find = view.findViewById<RecyclerView>(R.id.RecycleviewOfFind)
        Recycler_View_Find.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = FindAdapter()
        }

        BuscarTours("", "", "", view)
    }

    companion object {
        fun newInstance(): TourFragment = TourFragment()
    }

    fun BuscarTours(find: String, desde: String, hasta: String, view: View) {
        val service = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(TourService::class.java)
        service.SearchTour(find, desde, hasta).enqueue(object : Callback<Array<TourDto>> {

            override fun onFailure(call: Call<Array<TourDto>>, t: Throwable) {

            }

            override fun onResponse(call: Call<Array<TourDto>>, response: Response<Array<TourDto>>) {

                var data = response
                var tours = data.body()
                if (tours != null) {
                    tours.forEach { tour ->

                        Tours.add(
                                TourDto(tour.title,
                                        tour.puntuacion,
                                        tour.duracion,
                                        tour.precio,
                                        tour.catOpiniones,
                                        tour.img)
                        )


                    }
                }

                var list_recycler_viewT = view.findViewById<RecyclerView>(R.id.RecycleviewOfTour)
                list_recycler_viewT.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = ToursListAdapter(Tours)
                }
            }

        })
    }
}