package com.example.cards

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cards.services.dto.TourDto
import java.util.Base64


class ToursListAdapter(private val list: List<TourDto>)
    : RecyclerView.Adapter<TourViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TourViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {
        val tour: TourDto = list[position]
        holder.bind(tour)
    }

    override fun getItemCount(): Int = list.size

}

class TourViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.card_tour, parent, false)) {
    private var itemImage: ImageView
    private var itemTitle: TextView
    private var itemValor: TextView
    private var itemDuracion: TextView
    private var itemCantOpiniones: TextView
    private var itemStars: RatingBar


    init {
        itemImage = itemView.findViewById(R.id.imageView)
        itemTitle = itemView.findViewById(R.id.avatarName)
        itemValor = itemView.findViewById(R.id.valor)
        itemDuracion = itemView.findViewById(R.id.duracion)
        itemCantOpiniones = itemView.findViewById(R.id.countOpiniones)
        itemStars = itemView.findViewById(R.id.ratingbar)
    }

    fun bind(tour: TourDto) {
        val decoded = Base64.getDecoder().decode(tour.img)
        val decodedImage = BitmapFactory.decodeByteArray(decoded, 0, decoded.size)
        itemImage.setImageBitmap(decodedImage)
        itemTitle.text = tour.title
        itemDuracion.text = "Duración: "+tour.duracion
        itemValor.text = "₡"+tour.precio
        itemCantOpiniones.text = "Opiniones: "+tour.catOpiniones
        itemStars.rating = tour.puntuacion
    }

}