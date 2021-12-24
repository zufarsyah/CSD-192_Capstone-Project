package com.dicoding.picodiploma.findbinar.register

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.dicoding.picodiploma.findbinar.databinding.FragmentChangePasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException


class ChangePasswordFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding : FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        binding.layoutPassword.visibility = View.VISIBLE
        binding.layoutNewPassword.visibility = View.GONE

        binding.btnAuth.setOnClickListener {
            val password = binding.etPassword.text.toString().trim()
            if (password.isEmpty()) {
                binding.etPassword.error = "Password Cant Be Empty"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }

            //Checking user
            user?.let {
                val userCredential = EmailAuthProvider.getCredential(it.email!!, password)
                it.reauthenticate(userCredential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.layoutPassword.visibility = View.GONE
                        binding.layoutNewPassword.visibility = View.VISIBLE
                    }else if(it.exception is FirebaseAuthInvalidCredentialsException) {
                        binding.etPassword.error = "Invalid Password"
                        binding.etPassword.requestFocus()
                    }else {
                        Toast.makeText(activity, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            binding.btnUpdate.setOnClickListener { view ->
                val newPassword = binding.etNewPassword.text.toString().trim()
                val newPasswordConfirm = binding.etNewPasswordConfirm.text.toString().trim()

                if (newPassword.isEmpty() || newPassword.length < 6) {
                    binding.etNewPassword.error = "Password must more than six character"
                    binding.etNewPassword.requestFocus()
                    return@setOnClickListener
                }

                if (newPassword != newPasswordConfirm) {
                    binding.etNewPasswordConfirm.error = "Password does not match"
                    binding.etNewPasswordConfirm.requestFocus()
                    return@setOnClickListener
                }

                user?.let {
                    user.updatePassword(newPassword).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val actionPasswordChanged = ChangePasswordFragmentDirections.actionPasswordChanged()
                            Navigation.findNavController(view).navigate(actionPasswordChanged)
                            Toast.makeText(activity, "Password changed successfull", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(activity, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

}