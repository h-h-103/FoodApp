package com.example.foodapp.adpters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.FavoriteItemBinding
import com.example.foodapp.model.Mealz

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(val binding: FavoriteItemBinding): RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object: DiffUtil.ItemCallback<Mealz>() {
        override fun areItemsTheSame(oldItem: Mealz, newItem: Mealz): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Mealz, newItem: Mealz): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val mealz = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(mealz.strMealThumb)
            .into(holder.binding.imgFavorite)

        holder.binding.tvFavorite.text = mealz.strMeal
    }
}