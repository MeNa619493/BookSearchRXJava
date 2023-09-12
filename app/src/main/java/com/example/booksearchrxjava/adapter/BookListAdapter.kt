package com.example.booksearchrxjava.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booksearchrxjava.databinding.ItemBookBinding
import com.example.booksearchrxjava.network.VolumeInfo

class BookListAdapter : ListAdapter<VolumeInfo, BookListAdapter.MyViewHolder>(AirlineDiffCallback()) {

    class MyViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: VolumeInfo) {
            binding.tvBookTitle.text = data.volumeInfo.title
            binding.tvBookPublisher.text = data.volumeInfo.publisher
            binding.tvBookDesc.text = data.volumeInfo.description

            val url  = data .volumeInfo.imageLinks.smallThumbnail
            Glide.with(binding.ivBookImage)
                .load(url)
                .circleCrop()
                .into(binding.ivBookImage)
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBookBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AirlineDiffCallback : DiffUtil.ItemCallback<VolumeInfo>() {
        override fun areItemsTheSame(oldItem: VolumeInfo, newItem: VolumeInfo): Boolean {
            return oldItem.volumeInfo.title == newItem.volumeInfo.title
        }

        override fun areContentsTheSame(oldItem: VolumeInfo, newItem: VolumeInfo): Boolean {
            return oldItem == newItem
        }
    }
}