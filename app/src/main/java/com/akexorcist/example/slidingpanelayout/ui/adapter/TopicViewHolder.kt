package com.akexorcist.example.slidingpanelayout.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.example.slidingpanelayout.databinding.ViewTopicBinding
import com.akexorcist.example.slidingpanelayout.vo.Book

class TopicViewHolder(private val binding: ViewTopicBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(book: Book, isSelected: Boolean, onClick: () -> Unit) {
        binding.buttonTopic.text = book.title
        binding.buttonTopic.isSelected = isSelected
        binding.buttonTopic.setOnClickListener { onClick() }
    }
}
