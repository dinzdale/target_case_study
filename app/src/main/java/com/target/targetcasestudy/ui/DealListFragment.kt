package com.target.targetcasestudy.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.target.targetcasestudy.R
import com.target.targetcasestudy.network.DealsViewModel


class DealListFragment : Fragment() {

    lateinit var dealsViewModel: DealsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_deal_list, container, false)

        view.findViewById<RecyclerView>(R.id.recycler_view).layoutManager =
            LinearLayoutManager(requireContext())

        dealsViewModel = ViewModelProvider(requireActivity()).get(DealsViewModel::class.java)
        dealsViewModel.retrieveDeals().observe(viewLifecycleOwner) {
            it.onSuccess {
                view.findViewById<RecyclerView>(R.id.recycler_view).adapter = DealItemAdapter(it.products)
                Log.d("","${it.products.size} found")
            }
            it.onFailure {
                Log.d("",it.message,it)
            }
        }
        return view
    }
}
