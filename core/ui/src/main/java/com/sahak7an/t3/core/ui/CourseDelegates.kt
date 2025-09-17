package com.sahak7an.t3.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.sahak7an.t3.core.ui.databinding.ItemCourseBinding

fun createCourseAdapter(onToggleFavorite: (UiCourse) -> Unit): ListDelegationAdapter<List<UiCourse>> {
    val delegate = adapterDelegateViewBinding<UiCourse, UiCourse, ItemCourseBinding>({ inflater, parent ->
        ItemCourseBinding.inflate(inflater, parent, false)
    }) {
        bind {
            binding.title.text = item.title
            binding.description.text = item.text
            val priceText = if (item.price.trim().endsWith("₽")) item.price.trim() else item.price.trim() + " ₽"
            binding.price.text = priceText
            binding.like.setImageResource(
                if (item.isFavorite) R.drawable.ic_bookmark_filled_green_24 else R.drawable.ic_bookmark_outline_24
            )
            binding.startDate.text = item.startDate
            binding.rate.text = item.rate
            binding.like.setOnClickListener { onToggleFavorite(item) }
        }
    }
    return ListDelegationAdapter(delegate)
}


