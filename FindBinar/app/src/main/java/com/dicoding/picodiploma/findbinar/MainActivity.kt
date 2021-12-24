package com.dicoding.picodiploma.findbinar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.picodiploma.findbinar.databinding.ActivityMainBinding
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.dicoding.picodiploma.findbinar.ui.home.HomeActivity
import android.content.Intent
import android.util.Patterns
import com.dicoding.picodiploma.findbinar.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
//    private var FirebaseAuth mAuth;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        var mEmail = findViewById<EditText>(R.id.editTextEmail)
        var mPassword = findViewById<EditText>(R.id.editTextPassword)
        var btnLogin = findViewById<Button>(R.id.LoginButton)
        btnLogin.setOnClickListener {

            val email = mEmail.text.toString().trim()
            val password = mPassword.text.toString().trim()

            if (email.isEmpty()) {
                mEmail.error = "Email Cant Be Empty"
                mEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                mEmail.error = "Email Not Valid"
                mEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                mPassword.error = "Password must more than 6 character"
                mPassword.requestFocus()
                return@setOnClickListener
            }

            loginUser(email, password)

        }

        var btnSignUp = findViewById<Button>(R.id.btnSignUp)
        btnSignUp.setOnClickListener {
            val moveIntent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(moveIntent)
            Toast.makeText(applicationContext, "Register Activity", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Intent(this@MainActivity, HomeActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            Intent(this@MainActivity, HomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }
}