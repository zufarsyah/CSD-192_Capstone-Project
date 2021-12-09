package com.dicoding.picodiploma.findbinar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.picodiploma.findbinar.databinding.ActivityDetailWebinarAcitvityBinding


class DetailWebinarAcitvity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailWebinarAcitvityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWebinarAcitvityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}