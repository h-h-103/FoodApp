package com.example.foodapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.adpters.CategoryAdapter
import com.example.foodapp.databinding.FragmentCategoriesBinding
import com.example.foodapp.viewmodel.MealzViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private val binding by lazy { FragmentCategoriesBinding.inflate(layoutInflater) }
    private val categoryAdapter by lazy { CategoryAdapter() }
    private val viewModel: MealzViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerCategories()
        observeCategories()
        onCategoryClick()
    }

    private fun recyclerCategories() {
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    private fun observeCategories() {
        // Fetch the categories when the view is created
        viewModel.fetchCategories()
        lifecycleScope.launchWhenStarted {
            viewModel.categories.collect { categoryResponse ->
                categoryResponse?.categories?.let { categoriesList ->
                    // Update the adapter's data without re-assigning it
                    categoryAdapter.setCategoryList(ArrayList(categoriesList))
                }
            }
        }
    }

    private fun onCategoryClick() {
        categoryAdapter.onItemClick = {
            Toast.makeText(requireContext(), it.strCategory, Toast.LENGTH_SHORT).show()
        }
    }
}