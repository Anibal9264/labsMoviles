package com.example.cards

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ToursListAdapter(private val list: List<Tour>)
    : RecyclerView.Adapter<TourViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TourViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {
        val tour: Tour = list[position]
        holder.bind(tour)
    }

    override fun getItemCount(): Int = list.size

}

class TourViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.card_tour, parent, false)) {
    private var itemImage: ImageView
    private var itemTitle: TextView
    private var itemValor: TextView
    private var itemLugar: TextView
    private var itemCantOpiniones: TextView
    private var itemStars: RatingBar


    init {
        itemImage = itemView.findViewById(R.id.imageView)
        itemTitle = itemView.findViewById(R.id.avatarName)
        itemValor = itemView.findViewById(R.id.valor)
        itemLugar = itemView.findViewById(R.id.lugar)
        itemCantOpiniones = itemView.findViewById(R.id.countOpiniones)
        itemStars = itemView.findViewById(R.id.ratingbar)
    }

    fun bind(tour: Tour) {
        itemImage.setImageResource(tour.image)
        itemTitle.text = tour.title
        itemLugar.text = tour.lugar
        itemValor.text = "$"+tour.precios.toString()
        itemCantOpiniones.text = "Opiniones: "+tour.catOpiniones
        itemStars.rating = tour.puntuacion
    }

}