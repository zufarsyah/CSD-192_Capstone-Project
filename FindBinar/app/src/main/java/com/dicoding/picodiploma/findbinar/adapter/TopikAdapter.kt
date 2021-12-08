package com.dicoding.picodiploma.findbinar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.findbinar.data.Topik
import com.dicoding.picodiploma.findbinar.R

class TopikAdapter(private val listTopik: ArrayList<Topik>): RecyclerView.Adapter<TopikAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_topik, parent, false)
        return ListViewHolder(view)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val(topik, icon) = listTopik[position]
        holder.imgIcon.setImageResource(icon)
        holder.tvTopik.text = topik
    }

    override fun getItemCount(): Int {
        return listTopik.size
    }
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgIcon : ImageView = itemView.findViewById(R.id.topik_icon)
        var tvTopik: TextView = itemView.findViewById(R.id.topik_name)
    }


}