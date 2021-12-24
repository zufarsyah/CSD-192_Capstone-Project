package com.dicoding.picodiploma.findbinar.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.dicoding.picodiploma.findbinar.MainActivity
import com.dicoding.picodiploma.findbinar.R
import com.dicoding.picodiploma.findbinar.databinding.ActivityRegisterBinding
import com.dicoding.picodiploma.findbinar.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        var mEmail = findViewById<EditText>(R.id.edtEmail)
        var mPassword = findViewById<EditText>(R.id.edtPassword)
        var btnRegister = findViewById<Button>(R.id.RegisterButton)
        btnRegister.setOnClickListener {
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

            if (password.isEmpty() || password.length < 6 ) {
                mPassword.error = "Password must more than six character"
                mPassword.requestFocus()
                return@setOnClickListener
            }


            registerUser(email, password)
        }

        var btnLogUp = findViewById<Button>(R.id.btnLogUp)
        btnLogUp.setOnClickListener {
            val moveIntent = Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(moveIntent)
            Toast.makeText(applicationContext, "Log in Activity", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Intent(this@RegisterActivity, HomeActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                }else  {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            Intent(this@RegisterActivity, HomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }
}