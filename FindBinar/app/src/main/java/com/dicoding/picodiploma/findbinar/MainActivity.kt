package com.dicoding.picodiploma.findbinar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.dicoding.picodiploma.findbinar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        var btnLogin = findViewById<Button>(R.id.LoginButton)
        btnLogin.setOnClickListener {
            var email = findViewById<EditText>(R.id.editTextEmail)
            var password = findViewById<EditText>(R.id.editTextPassword)

            if (email.text.toString().equals("admin") && password.text.toString().equals("123")) {
                val moveIntent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(moveIntent)
                Toast.makeText(applicationContext, "Login Sukses", Toast.LENGTH_SHORT).show()
                finish()
            }else {
                Toast.makeText(applicationContext, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}