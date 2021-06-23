package com.example.cards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
class FindAdapter : RecyclerView.Adapter<FindViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FindViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: FindViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 1

}

class FindViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.card_find, parent, false)) {
}