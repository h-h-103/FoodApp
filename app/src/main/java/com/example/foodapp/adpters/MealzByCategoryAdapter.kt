package com.example.foodapp.adpters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.MealByCategoryItemBinding
import com.example.foodapp.model.MealzByCategory

class MealzByCategoryAdapter: RecyclerView.Adapter<MealzByCategoryAdapter.MealzByCategoryViewHolder>() {

    private var mealsList = ArrayList<MealzByCategory>()

    inner class MealzByCategoryViewHolder(val binding: MealByCategoryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealzByCategoryAdapter.MealzByCategoryViewHolder {
        return MealzByCategoryViewHolder(MealByCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = mealsList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MealzByCategoryAdapter.MealzByCategoryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgByCategory)

        holder.binding.tvByCategory.text = mealsList[position].strMeal
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMealsByCategory(mealsList: List<MealzByCategory>) {
        this.mealsList.clear()
        this.mealsList.addAll(mealsList)
        notifyDataSetChanged()
    }
}