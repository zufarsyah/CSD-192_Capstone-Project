package com.dicoding.picodiploma.findbinar.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.findbinar.Home.ListWebinarAdapter.*
import com.dicoding.picodiploma.findbinar.R

class ListWebinarAdapter(private val listWebinar: ArrayList<Webinar>) : RecyclerView.Adapter<ListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
       val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_webinar, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (title, university, photo) = listWebinar[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvTitle.text = title
        holder.tvUniversity.text = university
    }

    override fun getItemCount(): Int = listWebinar.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto : ImageView = itemView.findViewById(R.id.img_item_photo)
        var tvTitle: TextView = itemView.findViewById(R.id.tv_item_title)
        var tvUniversity : TextView = itemView.findViewById(R.id.tv_item_university)
    }

}