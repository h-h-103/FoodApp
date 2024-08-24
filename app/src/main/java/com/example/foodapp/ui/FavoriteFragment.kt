package com.example.foodapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.adpters.FavoriteAdapter
import com.example.foodapp.databinding.FragmentFavoriteBinding
import com.example.foodapp.viewmodel.MealzViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val binding by lazy { FragmentFavoriteBinding.inflate(layoutInflater) }
    private val favoriteAdapter by lazy { FavoriteAdapter() }
    private val viewModel: MealzViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerFavorite()
        observeFavorite()
        setupSwipe()
    }

    private fun recyclerFavorite() {
        binding.rvFavorite.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = favoriteAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeFavorite() {
        // Fetch the favorite meals when the view is created
        viewModel.getMealz()
        lifecycleScope.launchWhenStarted {
            viewModel.getMealz.collect { mealzList ->
                mealzList?.let {
                    favoriteAdapter.differ.submitList(it)
                }
            }
        }
    }

    private fun setupSwipe() {
        val callBack = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            @SuppressLint("NotifyDataSetChanged", "ShowToast")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedMealzPosition: Int = viewHolder.adapterPosition
                val swipedMealz = favoriteAdapter.differ.currentList[swipedMealzPosition]
                viewModel.deleteMealz(swipedMealz)
                favoriteAdapter.differ.submitList(favoriteAdapter.differ.currentList - swipedMealz)
                Snackbar.make(requireView(), "Mealz Deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        viewModel.upsertMealz(swipedMealz)
                        favoriteAdapter.differ.submitList(favoriteAdapter.differ.currentList + swipedMealz)
                    }.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(callBack)
        itemTouchHelper.attachToRecyclerView(binding.rvFavorite)
    }
}