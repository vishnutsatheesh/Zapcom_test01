package com.app.zapcomtest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.zapcomtest.R
import com.app.zapcomtest.data.Item
import com.app.zapcomtest.data.Section
import com.bumptech.glide.Glide

class SectionAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var sections = arrayListOf<Section>()

    companion object {
        private const val TYPE_BANNER = 0
        private const val TYPE_HORIZONTAL_SCROLL = 1
        private const val TYPE_SPLIT_BANNER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (sections[position].sectionType) {
            "banner" -> TYPE_BANNER
            "horizontalFreeScroll" -> TYPE_HORIZONTAL_SCROLL
            "splitBanner" -> TYPE_SPLIT_BANNER
            else -> throw IllegalArgumentException("Invalid section type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_BANNER -> {
                val view = inflater.inflate(R.layout.item_banner, parent, false)
                BannerViewHolder(view)
            }

            TYPE_HORIZONTAL_SCROLL -> {
                val view = inflater.inflate(R.layout.item_horizontal_scroll, parent, false)

                HorizontalScrollViewHolder(view)
            }

            TYPE_SPLIT_BANNER -> {
                val view = inflater.inflate(R.layout.item_split_banner, parent, false)
                SplitBannerViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BannerViewHolder -> holder.bind(sections[position])
            is HorizontalScrollViewHolder -> holder.bind(sections[position])
            is SplitBannerViewHolder -> holder.bind(sections[position])
        }
    }

    override fun getItemCount(): Int = sections.size

    fun setSections(data: List<Section>) {
        try {
            sections.clear()
            if (data.isNotEmpty()) {
                sections.addAll(data)
            }
            notifyDataSetChanged()
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    // ViewHolder for Banner section
    inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.imageView)

        fun bind(section: Section) {
            val item = section.items.first()
            Glide.with(imageView.context).load(item.image).into(imageView)
        }
    }

    // ViewHolder for Horizontal Scroll section
    inner class HorizontalScrollViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val recyclerView: RecyclerView = view.findViewById(R.id.rcImages)

        init {
            recyclerView.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.setHasFixedSize(true)
        }

        fun bind(section: Section) {
            recyclerView.adapter = HorizontalAdapter(section.items)
        }
    }

    // Adapter for Horizontal Scroll items
    inner class HorizontalAdapter(private val items: List<Item>) :
        RecyclerView.Adapter<HorizontalAdapter.HorizontalItemViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): HorizontalItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image, parent, false)

            return HorizontalItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: HorizontalItemViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size

        inner class HorizontalItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val imageView: ImageView = view.findViewById(R.id.imageView)

            fun bind(item: Item) {
                Glide.with(imageView.context).load(item.image).into(imageView)
            }
        }
    }

    // ViewHolder for Split Banner section
    inner class SplitBannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val leftImageView: ImageView = view.findViewById(R.id.leftImageView)
        private val rightImageView: ImageView = view.findViewById(R.id.rightImageView)

        fun bind(section: Section) {
            val leftItem = section.items[0]
            val rightItem = section.items[1]

            Glide.with(leftImageView.context).load(leftItem.image).into(leftImageView)
            Glide.with(rightImageView.context).load(rightItem.image).into(rightImageView)
        }
    }
}