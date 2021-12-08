package com.dicoding.picodiploma.findbinar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.findbinar.home.GridPhotoAdapter
import com.dicoding.picodiploma.findbinar.data.Webinar
import com.dicoding.picodiploma.findbinar.databinding.FragmentHomeBinding
import com.dicoding.picodiploma.findbinar.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var rvWebinar: RecyclerView
    private lateinit var rvPhoto : RecyclerView
    private var list = ArrayList<Webinar>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        rvWebinar = binding.rvWebinar
        rvWebinar.setHasFixedSize(true)
        rvPhoto = binding.photoList

        list.addAll(listWebinar)
        showRecyclerList()
        showRecyclerGrid()

        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val listWebinar: ArrayList<Webinar>
        get() {
            val dataTitle = resources.getStringArray(R.array.data_title)
            val dataUniversity = resources.getStringArray(R.array.data_univ)
            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            val listBinar = ArrayList<Webinar>()
            for (i in dataTitle.indices) {
                val webinar = com.dicoding.picodiploma.findbinar.data.Webinar(
                    dataTitle[i],
                    dataUniversity[i],
                    dataPhoto.getResourceId(i, -1)
                )
                listBinar.add(webinar)
            }
            return listBinar
        }
    private fun showRecyclerList() {
        rvWebinar.layoutManager = LinearLayoutManager(activity)
        val listWebinarAdapter = com.dicoding.picodiploma.findbinar.adapter.ListWebinarAdapter(list)
        rvWebinar.adapter = listWebinarAdapter
    }
    private fun showRecyclerGrid() {
        rvPhoto.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val gridPhotoAdapter = GridPhotoAdapter(list)
        rvPhoto.adapter = gridPhotoAdapter


    }

}