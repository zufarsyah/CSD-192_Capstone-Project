package com.dicoding.picodiploma.findbinar.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.findbinar.R

class GridPhotoAdapter(val listWebinar : ArrayList<Webinar>): RecyclerView.Adapter<GridPhotoAdapter.GridViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GridViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_photo, parent, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(listWebinar[position].photo)
            .apply(RequestOptions().override(350, 200))
            .into(holder.imgPhoto)
    }

    override fun getItemCount(): Int {
        return listWebinar.size
    }

    class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto : ImageView = itemView.findViewById(R.id.img_photo)
    }




}


