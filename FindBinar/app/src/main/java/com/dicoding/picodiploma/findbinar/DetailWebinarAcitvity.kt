package com.dicoding.picodiploma.findbinar

import android.content.Intent
import android.net.Uri
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
        binding.tvPartisipanWebinar.text = String.format(resources.getString(R.string.partisipan), webinar.partisipan)
        binding.tvTopikWebinar.text = webinar.topik
        binding.tvDeskripsiWebinar.text = webinar.deskripsi

        binding.callPhone.setOnClickListener {
            val phoneNumber = "081210841382"
            val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            startActivity(dialPhoneIntent)
        }
        binding.btnLink.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.qubisa.com/webinars")
                )
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_WEBINAR = "extra_webinar"
    }
}