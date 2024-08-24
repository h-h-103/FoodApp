package com.example.foodapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.adpters.MealzByCategoryAdapter
import com.example.foodapp.databinding.FragmentMealzByCategoryBinding
import com.example.foodapp.viewmodel.MealzViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MealzByCategoryFragment : Fragment() {

    private val binding by lazy { FragmentMealzByCategoryBinding.inflate(layoutInflater) }
    private val mealzByCategoryAdapter by lazy { MealzByCategoryAdapter() }
    private val viewModel: MealzViewModel by viewModels()
    private val args: MealzByCategoryFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerMealByCategory()
        observeMealByCategory()
    }


    private fun recyclerMealByCategory() {
        binding.mealRecyclerview.apply {
            layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL, false)
            adapter = mealzByCategoryAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeMealByCategory() {
        // Fetch the meal by category when the view is created
        viewModel.fetchMealzByCategory(args.categoryName)
        lifecycleScope.launchWhenStarted {
            viewModel.mealzByCategory.collect { categoryResponse ->
                categoryResponse?.meals?.let { mealsList ->
                    mealzByCategoryAdapter.setMealsByCategory(ArrayList(mealsList))
                    binding.tvCategoryCount.text = "${args.categoryName} : ${mealsList.size}"
                }
            }
        }
    }
}