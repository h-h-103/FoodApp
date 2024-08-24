package com.example.foodapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.FragmentMealzBinding
import com.example.foodapp.model.Mealz
import com.example.foodapp.viewmodel.MealzViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MealzFragment : Fragment() {

    private val binding by lazy { FragmentMealzBinding.inflate(layoutInflater) }
    private val args: MealzFragmentArgs by navArgs()
    private val viewModel: MealzViewModel by viewModels()
    private lateinit var youtubeLink: String
    private lateinit var mealzToSave: Mealz

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeMealDetails()
        setupYouTubeLink()
        fetchDetailsMealz()
        insertMealz()
    }

    private fun setupViews() {
        // Setting up basic information
        Glide.with(requireContext()).load(args.mealThumb).into(binding.imgMealDetail)
        binding.collapsingToolbar.title = args.mealName
        // Show the loading state initially
        onLoading()
    }

    @SuppressLint("SetTextI18n")
    private fun observeMealDetails() {
        lifecycleScope.launchWhenStarted {
            viewModel.mealDetails.collect { mealzList ->
                mealzList?.meals?.randomOrNull()?.let { detailsMeal ->
                    mealzToSave = detailsMeal
                    // Hide loading and show content
                    onResponse()
                    binding.tvCategoryInfo.text = "Category : ${detailsMeal.strCategory}"
                    binding.tvAreaInfo.text = "Area : ${detailsMeal.strArea}"
                    binding.tvInstructions.text = detailsMeal.strInstructions

                    // Store the YouTube link
                    youtubeLink = detailsMeal.strYoutube!!
                }
            }
        }
    }

    private fun fetchDetailsMealz() {
        // Fetch meal details when the view is created
        viewModel.fetchMealDetails(args.mealId)
    }

    private fun onLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
    }

    private fun onResponse() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
    }

    private fun setupYouTubeLink() {
        binding.imgYoutube.setOnClickListener {
            if (::youtubeLink.isInitialized && youtubeLink.isNotEmpty()) {
                val intentMealz = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
                startActivity(intentMealz)
            } else {
                Log.d("ErrorYouTubeLink", "YouTube link is not initialized or empty.")
            }
        }
    }

    @SuppressLint("ShowToast")
    private fun insertMealz() {
        // Insert the meal into the database
        binding.btnSave.setOnClickListener {
            mealzToSave.let {
                viewModel.upsertMealz(it)
                Snackbar.make(requireView(), "Mealz Saved", Snackbar.LENGTH_LONG)
                    .setAction("Ok") {}.show()
            }
        }
    }
}