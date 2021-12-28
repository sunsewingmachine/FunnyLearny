package com.local.funnylearny.ui.themes_colors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.local.funnylearny.databinding.ThemesAndColorsListItemBinding

class ThemesAndColorsRecyclerViewAdapter() : RecyclerView.Adapter<ThemesAndColorsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(ThemesAndColorsListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 5


    class ViewHolder(binding : ThemesAndColorsListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val themesAndColorsColorOne : CardView = binding.themesAndColorsColorOne
        val themesAndColorsColorTwo : CardView = binding.themesAndColorsColorTwo
        val themesAndColorsColorThree : CardView = binding.themesAndColorsColorThree
        val themesAndColorsColorFour : CardView = binding.themesAndColorsColorFour
        val themesAndColorsColorFive : CardView = binding.themesAndColorsColorFive
    }
    }
