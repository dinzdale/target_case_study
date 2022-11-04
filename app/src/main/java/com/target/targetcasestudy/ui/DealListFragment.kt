package com.target.targetcasestudy.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.target.targetcasestudy.R
import com.target.targetcasestudy.network.DealsViewModel


class DealListFragment : Fragment() {

    val dealsViewModel: DealsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_deal_list, container, false)

        view.findViewById<RecyclerView>(R.id.recycler_view).layoutManager =
            LinearLayoutManager(requireContext())

        dealsViewModel.retrieveDeals().observe(viewLifecycleOwner) {
            it.onSuccess {
                view.findViewById<RecyclerView>(R.id.recycler_view).adapter =
                    DealItemAdapter(it.products, requireContext()) { row, id ->
                        dealsViewModel.selecedDealId = id
                        findNavController().navigate(R.id.action_dealListFragment_to_productDetailsFragment)
                    }
            }
            it.onFailure {
                Log.d("", it.message, it)
            }
        }
        return view
    }
}
