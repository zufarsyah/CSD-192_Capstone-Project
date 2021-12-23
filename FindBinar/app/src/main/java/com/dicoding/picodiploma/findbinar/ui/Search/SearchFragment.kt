package com.dicoding.picodiploma.findbinar.ui.Search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.findbinar.adapter.ListWebinarAdapter
import com.dicoding.picodiploma.findbinar.adapter.TopikAdapter
import com.dicoding.picodiploma.findbinar.data.Topik
import com.dicoding.picodiploma.findbinar.data.Webinar
import com.dicoding.picodiploma.findbinar.R
import com.dicoding.picodiploma.findbinar.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var dashboardViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null
    private lateinit var rvTopik : RecyclerView
    private lateinit var rvDiikuti : RecyclerView
    private var list = ArrayList<Topik>()
    private val binding get() = _binding!!
    private var listBinar = ArrayList<Webinar>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rvTopik = binding.rvTopik
        rvDiikuti = binding.rvDiikuti
        rvDiikuti.setHasFixedSize(true)

        list.addAll(listTopik)
        listBinar.addAll(listWebinar)
        showRecyclerTopik()
        showRecyclerList()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        val listTopikAdapter = TopikAdapter(list)
        rvTopik.adapter = listTopikAdapter
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
        rvDiikuti.layoutManager = LinearLayoutManager(activity)
        val listWebinarAdapter = ListWebinarAdapter(listBinar)
        rvDiikuti.adapter = listWebinarAdapter
    }
}