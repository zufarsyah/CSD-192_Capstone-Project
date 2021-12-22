package com.dicoding.picodiploma.findbinar.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.findbinar.MainActivity
import com.dicoding.picodiploma.findbinar.R
import com.dicoding.picodiploma.findbinar.adapter.ListWebinarAdapter
import com.dicoding.picodiploma.findbinar.data.Webinar
import com.dicoding.picodiploma.findbinar.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private lateinit var rvRiwayat: RecyclerView
    private var listFindBinar = ArrayList<Webinar>()
    private lateinit var auth: FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rvRiwayat = binding.rvHistory
        rvRiwayat.setHasFixedSize(true)

        listFindBinar.addAll(listWebinar)
        showRecyclerList()

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val listWebinar: ArrayList<Webinar>
        get() {
            val dataTitle = resources.getStringArray(R.array.data_title)
            val dataDate = resources.getStringArray(R.array.data_date)
            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            val dataTopik = resources.getStringArray(R.array.data_topik)
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
                    dataPhoto.getResourceId(i, -1)
                )
                listBinar.add(webinar)
            }
            return listBinar
        }

    private fun showRecyclerList() {
        rvRiwayat.layoutManager = LinearLayoutManager(activity)
        val webinar = ListWebinarAdapter(listFindBinar)
        rvRiwayat.adapter = webinar
    }

}