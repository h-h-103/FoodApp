package com.example.foodapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.adpters.FavoriteAdapter
import com.example.foodapp.databinding.FragmentSearchBinding
import com.example.foodapp.viewmodel.MealzViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

@Suppress("DEPRECATION")
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private lateinit var adapterSearch: FavoriteAdapter
    private val viewModel: MealzViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerSearchMeals()
        searchMeals()
        observeSearchMeals()

    }

    private fun recyclerSearchMeals() {
        adapterSearch = FavoriteAdapter()
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = adapterSearch
        }
    }

    private fun searchMeals() {
        var jop: Job? = null
        binding.edSearch.addTextChangedListener { searchQuery ->
            jop?.cancel()
            jop = lifecycleScope.launchWhenStarted {
                delay(500)
                viewModel.fetchSearchMealz(searchQuery.toString())
            }
        }
    }

    private fun observeSearchMeals() {
        lifecycleScope.launchWhenStarted {
            viewModel.searchMealz.collect { response ->
                response?.meals?.let { mealsList ->
                    adapterSearch.differ.submitList(mealsList)
                }
            }
        }
    }

}