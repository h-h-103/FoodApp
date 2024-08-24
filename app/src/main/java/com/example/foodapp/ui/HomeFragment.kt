package com.example.foodapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.adpters.CategoryAdapter
import com.example.foodapp.adpters.PopularAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.model.Category
import com.example.foodapp.model.MealzByCategory
import com.example.foodapp.model.Mealz
import com.example.foodapp.viewmodel.MealzViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val navController by lazy { findNavController() }
    private val popularAdapter by lazy { PopularAdapter() }
    private val categoryAdapter by lazy { CategoryAdapter() }
    private val viewModel: MealzViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchMeals()
        observeRandomMeal()
        setupClickListenersRandom()
        recyclerPopularMeals()
        observePopularMeal()
        setupClickListenersPopularItem()
        onPopularItemLongClick()
        recyclerCategories()
        observeCategories()
        setupClickListenersCategory()
    }

    private fun searchMeals() {
        binding.imgSearch.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            navController.navigate(action)
        }
    }

    // Observes the random meal and updates the UI
    private fun observeRandomMeal() {
        // Fetch the random meal when the view is created
        viewModel.fetchRandomMeal()
        lifecycleScope.launchWhenStarted {
            viewModel.randomMeal.collect { mealzList ->
                mealzList?.meals?.randomOrNull()?.let { randomMeal ->
                    updateMealUI(randomMeal)
                }
            }
        }
    }

    private fun updateMealUI(mealz: Mealz) {
        Glide.with(this)
            .load(mealz.strMealThumb)
            .into(binding.imgRandomMeal)
        // Store the meal as a tag for easy retrieval
        binding.randomMeal.tag = mealz
    }

    // Sets up the click listeners for navigation
    private fun setupClickListenersRandom() {
        binding.randomMeal.setOnClickListener { it ->
            val mealz = it.tag as? Mealz
            mealz?.let {
                val action = HomeFragmentDirections.actionHomeFragmentToMealzFragment(
                    mealId = it.idMeal,
                    mealName = it.strMeal!!,
                    mealThumb = it.strMealThumb!!
                )
                navController.navigate(action)
            } ?: run {
                Log.d("ErrorMeal", "Meal is null")
            }
        }
    }

    private fun recyclerPopularMeals() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }
    }

    private fun observePopularMeal() {
        // Fetch the popular items when the view is created
        viewModel.fetchPopularItems("Seafood")
        lifecycleScope.launchWhenStarted {
            viewModel.popularItems.collect { categoryResponse ->
                categoryResponse?.meals?.let { mealsList ->
                    popularAdapter.setMeals(mealsList)
                }
            }
        }
    }

    // Sets up the click listeners for popular items
    private fun setupClickListenersPopularItem() {
        popularAdapter.onItemClick = { it ->
            val mealzPopular = it as? MealzByCategory
            mealzPopular?.let {
                val action = HomeFragmentDirections.actionHomeFragmentToMealzFragment(
                    mealId = it.idMeal,
                    mealName = it.strMeal,
                    mealThumb = it.strMealThumb
                )
                navController.navigate(action)
            } ?: run {
                Log.d("ErrorPopularMeal", "Popular Meal is null")
            }
        }
    }

    // Sets up the long click listeners for popular items
    private fun onPopularItemLongClick() {
        popularAdapter.onLongItemClick = {
            val bottomSheetFragment = MealBottomSheetFragment()
            bottomSheetFragment.arguments = Bundle().apply {
                putString("mealId", it.idMeal)
            }
            bottomSheetFragment.show(childFragmentManager, "Meal Info")
        }
    }

    private fun recyclerCategories() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
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

    // Sets up the click listeners for categories
    private fun setupClickListenersCategory() {
        categoryAdapter.onItemClick = { it ->
            val category = it as? Category
            category?.let {
                val action = HomeFragmentDirections.actionHomeFragmentToMealzByCategoryFragment(
                    categoryName = it.strCategory
                )
                navController.navigate(action)
            } ?: run {
                Log.d("ErrorCategory", "Category is null")
            }
        }
    }
}