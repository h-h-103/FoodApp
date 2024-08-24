package com.example.foodapp.adpters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.PopularItemBinding
import com.example.foodapp.model.MealzByCategory

class PopularAdapter: RecyclerView.Adapter<PopularAdapter.PopularMealViewHolder>() {

    private val mealsList = ArrayList<MealzByCategory>()
    lateinit var onItemClick: ((MealzByCategory) -> Unit)
    lateinit var onLongItemClick: ((MealzByCategory) -> Unit)

    inner class PopularMealViewHolder(val binding: PopularItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = mealsList.size

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMeal)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }

        holder.itemView.setOnLongClickListener {
            onLongItemClick.invoke(mealsList[position])
            true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMeals(mealsList: List<MealzByCategory>) {
        this.mealsList.clear()
        this.mealsList.addAll(mealsList)
        notifyDataSetChanged()
    }
}