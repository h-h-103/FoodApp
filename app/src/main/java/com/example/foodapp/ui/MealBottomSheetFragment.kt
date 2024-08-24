package com.example.foodapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.FragmentMealBottomeSheetBinding
import com.example.foodapp.viewmodel.MealzViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MealBottomSheetFragment : BottomSheetDialogFragment() {

    private val binding by lazy { FragmentMealBottomeSheetBinding.inflate(layoutInflater) }
    private val viewModel: MealzViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View  = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        val mealId = arguments?.getString("mealId") ?: return

        viewModel.fetchMealDetailsLongClick(mealId)

        // Observe the meal details
        lifecycleScope.launchWhenStarted {
            viewModel.mealDetailsLongClick.collect { response ->
                response?.meals?.firstOrNull()?.let { meal ->
                    binding.apply {
                        Glide.with(requireContext()).load(meal.strMealThumb).into(imgCategory)
                        tvMealNameInBtmsheet.text = meal.strMeal
                        tvMealCategory.text = " ${meal.strCategory}"
                        tvMealCountry.text = " ${meal.strArea}"
                    }
                }
            }
        }
    }
}