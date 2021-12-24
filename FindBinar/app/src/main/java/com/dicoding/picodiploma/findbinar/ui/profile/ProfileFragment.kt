package com.dicoding.picodiploma.findbinar.ui.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.dicoding.picodiploma.findbinar.MainActivity
import com.dicoding.picodiploma.findbinar.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream


class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private lateinit var auth: FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        const val REQUEST_CAMERA = 100
    }

    private lateinit var imageUri : Uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        auth = FirebaseAuth.getInstance()
        binding.LogoutButton.setOnClickListener {
            auth.signOut()
                Intent(requireActivity(), MainActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                }
            }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivImage.setOnClickListener {
            intentCamera()
        }

        val user  = auth.currentUser
        if(user != null) {
            if (user.photoUrl !=  null) {
                Picasso.get().load(user.photoUrl).into(binding.ivProfile)
            }else {
                Picasso.get().load("https://picsum.photos/200/300").into(binding.ivProfile)
            }

            binding.etName.setText(user.displayName)
            binding.etEmail.setText(user.email)
            binding.tvNamaUser.setText(user.displayName)
        }

        binding.btnUpdate.setOnClickListener {
            val image = when {
                ::imageUri.isInitialized -> imageUri
                user?.photoUrl == null -> Uri.parse("https://picsum.photos/200/300")
                else  -> user.photoUrl
            }

            val name = binding.etName.text.toString().trim()
            if (name.isEmpty()) {
                binding.etName.error = "This field cant be empty"
                binding.etName.requestFocus()
                return@setOnClickListener
            }

            showLoading(true)
            UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(image)
                .build().also {
                    user?.updateProfile(it)?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(activity, "Profile Updated , Go Refresh Page", Toast.LENGTH_SHORT).show()
                            showLoading(false)
                        }else {
                            Toast.makeText(activity, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }

        binding.etEmail.setOnClickListener {
            val actionUpdateEmail = ProfileFragmentDirections.actionUpdateEmail()
            Navigation.findNavController(it).navigate(actionUpdateEmail)
        }
        binding.btnChangePassword.setOnClickListener {
            val actionChangePassword = ProfileFragmentDirections.actionChangePassword()
            Navigation.findNavController(it).navigate(actionChangePassword)
        }
    }


    //camera
    private fun intentCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            activity?.packageManager?.let {
                intent.resolveActivity(it).also {
                    startActivityForResult(intent, REQUEST_CAMERA )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImage(imgBitmap)
        }
    }

    private fun uploadImage(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref = FirebaseStorage.getInstance().reference.child( "img/${FirebaseAuth.getInstance().currentUser?.uid}")
        showLoading(true)
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()
        ref.putBytes(image)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener {
                        it.result?.let {
                            imageUri = it
                            binding.ivProfile.setImageBitmap(imgBitmap)
                            showLoading(false)
                        }
                    }
                }
            }
    }
    //camera
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun showLoading(isLoading: Boolean) {
            if (isLoading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }

}
