package com.dicoding.picodiploma.findbinar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.util.rangeTo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.findbinar.data.Webinar

class TopikActivity : AppCompatActivity() {

    private lateinit var rvWebinar: RecyclerView
    private var list = ArrayList<Webinar>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topik)

        rvWebinar = findViewById(R.id.rv_webinarCategory)
        rvWebinar.setHasFixedSize(true)

        list.addAll(listWebinar)
        showRecyclerList()
    }

    private val listWebinar: ArrayList<Webinar>
        get() {
            val dataTitle = resources.getStringArray(R.array.data_title)
            val dataDate = resources.getStringArray(R.array.data_date)
            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            val dataTopik = resources.getStringArray(R.array.data_topik)
            val dataPartisipan = resources.getStringArray(R.array.data_partisipan)
            val dataDeskripsi = resources.getStringArray(R.array.data_deskripsi)
            val listBinar = ArrayList<Webinar>()
            for (i in dataTitle.indices) {
                var n : Int = 0

                when {
                    i < 5 -> {
                        n = 0
                    }
                    i in 5..9 -> {
                        n = 1
                    }
                    i in 10..14 -> {
                        n = 2
                    }
                    i in 15..19 -> {
                        n = 3
                    }
                }
                val webinar = com.dicoding.picodiploma.findbinar.data.Webinar(
                    dataTitle[i],
                    dataDate[i],
                    dataTopik[n],
                    dataPartisipan[i],
                    dataDeskripsi[i],
                    dataPhoto.getResourceId(i, -1)
                )
                listBinar.add(webinar)
            }
            return listBinar
        }
    private fun showRecyclerList() {
        rvWebinar.layoutManager = LinearLayoutManager(this)
        val listWebinarAdapter = com.dicoding.picodiploma.findbinar.adapter.ListWebinarAdapter(list)
        rvWebinar.adapter = listWebinarAdapter
    }

}