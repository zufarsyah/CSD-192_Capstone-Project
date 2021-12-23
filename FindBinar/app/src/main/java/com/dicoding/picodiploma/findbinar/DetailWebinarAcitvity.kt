package com.dicoding.picodiploma.findbinar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.picodiploma.findbinar.data.Webinar
import com.dicoding.picodiploma.findbinar.databinding.ActivityDetailWebinarAcitvityBinding


class DetailWebinarAcitvity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailWebinarAcitvityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWebinarAcitvityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Webinar"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val webinar = intent.getParcelableExtra<Webinar>(EXTRA_WEBINAR) as Webinar

        binding.imgWebinar.setImageResource(webinar.photo)
        binding.tvNamaWebinar.text = webinar.title
        binding.tvTanggalWebinar.text = webinar.date
        binding.tvPartisipanWebinar.text = String.format(resources.getString(R.string.parisipan), webinar.partisipan)
        binding.tvTopikWebinar.text = webinar.topik
        binding.tvDeskripsiWebinar.text = webinar.deskripsi




    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_WEBINAR = "extra_webinar"
    }
}