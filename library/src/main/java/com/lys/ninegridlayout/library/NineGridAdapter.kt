package com.lys.ninegridlayout.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class NineGridAdapter(private val data: ArrayList<Any>): RecyclerView.Adapter<NineGridViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NineGridViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_nine_grid,parent,false)
        return NineGridViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: NineGridViewHolder, position: Int) {

    }

    abstract fun setLayout()

    abstract fun loadImage()

}