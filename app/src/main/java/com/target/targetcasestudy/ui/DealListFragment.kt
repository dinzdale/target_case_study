package com.target.targetcasestudy.ui

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Snackbar
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.target.targetcasestudy.R
import com.target.targetcasestudy.model.getErrorResponse
import com.target.targetcasestudy.network.DealsViewModel
import kotlinx.coroutines.*


class DealListFragment : Fragment() {

    private val dealsViewModel: DealsViewModel by activityViewModels()

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
                        findNavController().navigate(
                            R.id.action_dealListFragment_to_productDetailsFragment)
                    }
            }
            it.onFailure {
                Log.d("", it.message, it)
                lifecycleScope.launch {
                    com.google.android.material.snackbar.Snackbar.make(view, R.string.generic_error,
                        3 * 1000).show()
                    delay(5 * 1000)
                    requireActivity().finish()
                }
            }
        }
        return view
    }

    override fun onResume() {
        (requireActivity() as AppCompatActivity).apply {
            supportActionBar?.apply {
               title = context?.getString(R.string.action_bar_title_list)
            }
            supportActionBar?.show()
        }
        super.onResume()
    }

}
