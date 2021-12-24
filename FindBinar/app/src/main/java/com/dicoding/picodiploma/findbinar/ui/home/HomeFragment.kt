package com.dicoding.picodiploma.findbinar.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.findbinar.adapter.GridPhotoAdapter
import com.dicoding.picodiploma.findbinar.data.Webinar
import com.dicoding.picodiploma.findbinar.databinding.FragmentHomeBinding
import com.dicoding.picodiploma.findbinar.R
import com.dicoding.picodiploma.findbinar.TopikActivity
import com.dicoding.picodiploma.findbinar.adapter.TopikAdapter
import com.dicoding.picodiploma.findbinar.data.Topik

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var rvWebinar: RecyclerView
    private lateinit var rvPhoto : RecyclerView
    private lateinit var rvTopik : RecyclerView
    private var list = ArrayList<Webinar>()
    private var list2 = ArrayList<Topik>()

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

        rvTopik = binding.rvTopik
        list2.addAll(listTopik)

        list.addAll(listWebinar)
        showRecyclerList()
        showRecyclerGrid()
        showRecyclerTopik()

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
            val dataPartisipan = resources.getStringArray(R.array.data_partisipan)
            val dataDeskripsi = resources.getStringArray(R.array.data_deskripsi)
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
                    dataPartisipan[i],
                    dataDeskripsi[i],
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

    private val listTopik: ArrayList<Topik>
        get() {
            val dataTopik = resources.getStringArray(R.array.data_topik)
            val dataIcon = resources.obtainTypedArray(R.array.icon_topik)
            val list = ArrayList<Topik>()
            for (i in dataTopik.indices) {
                val topik = Topik(dataTopik[i], dataIcon.getResourceId(i, -1))
                list.add(topik)
            }
            return list
        }
    private fun showRecyclerTopik() {
        rvTopik.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val listTopikAdapter = TopikAdapter(list2)
        rvTopik.adapter = listTopikAdapter

        listTopikAdapter.setOnItemClickCallback(object : TopikAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Topik) {
                val moveIntent = Intent(activity, TopikActivity::class.java)
                startActivity(moveIntent)
            }
        })
    }

}