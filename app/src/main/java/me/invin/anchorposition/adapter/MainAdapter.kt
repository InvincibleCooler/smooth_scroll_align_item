package me.invin.anchorposition.adapter

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import me.invin.anchorposition.R
import me.invin.anchorposition.databinding.InnerBigItemBinding
import me.invin.anchorposition.databinding.InnerSmallItemBinding
import me.invin.anchorposition.databinding.MainListitemBinding
import me.invin.anchorposition.utils.dipToPixel
import me.invin.anchorposition.viewmodel.ColorData
import me.invin.anchorposition.viewmodel.MainWrappedData


class MainAdapter(private val activity: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TAG = "MainAdapter"

        const val VIEW_TYPE_JOB = 1
        const val VIEW_TYPE_ANIMAL = 2
        const val VIEW_TYPE_IDOL = 3
        const val VIEW_TYPE_COLOR = 4
    }

    // set horizontal scroll position
    val scrollStates: MutableMap<String, Parcelable?> = mutableMapOf()
    private val viewPool = RecyclerView.RecycledViewPool()

    @Suppress("UNCHECKED_CAST")
    fun getSectionID(position: Int) = items[position].id

    var items = mutableListOf<MainWrappedData>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyItemRangeChanged(0, value.size)
        }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is ItemViewHolder) {
            val key = getSectionID(holder.layoutPosition)
            scrollStates[key] = holder.binding.recyclerHorizontal.layoutManager?.onSaveInstanceState()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(MainListitemBinding.inflate(LayoutInflater.from(activity), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_JOB -> {
                val vh = holder as ItemViewHolder
                vh.isSmallItem = false
                vh.binding.tvTitle.text = "JOB"

                val innerWrappedDataList = mutableListOf<InnerWrappedData>()
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "Android Programmer"))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "iOS Programmer"))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "Web Programmer"))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "Unity Programmer"))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "Game Programmer"))
                vh.setItems(innerWrappedDataList)

                setScrollPosition(vh)
            }
            VIEW_TYPE_ANIMAL -> {
                val vh = holder as ItemViewHolder
                vh.isSmallItem = false
                vh.binding.tvTitle.text = "Animal"

                val innerWrappedDataList = mutableListOf<InnerWrappedData>()
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "Dog"))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "Cat"))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "Tiger"))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "Platypus"))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "Pig"))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "Cow"))
                vh.setItems(innerWrappedDataList)

                setScrollPosition(vh)
            }
            VIEW_TYPE_IDOL -> {
                val vh = holder as ItemViewHolder
                vh.isSmallItem = false
                vh.binding.tvTitle.text = "Idol"

                val innerWrappedDataList = mutableListOf<InnerWrappedData>()
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "아이유"))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "BTS"))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "오마이걸"))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "에스파"))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "소녀시대"))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "티티마"))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_BIG, data = "트와이스"))
                vh.setItems(innerWrappedDataList)

                setScrollPosition(vh)
            }
            VIEW_TYPE_COLOR -> {
                val vh = holder as ItemViewHolder
                vh.isSmallItem = true
                vh.binding.tvTitle.text = "Color"

                val innerWrappedDataList = mutableListOf<InnerWrappedData>()
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_SMALL, data = ColorData("white", "#FFFFFF")))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_SMALL, data = ColorData("blue", "#0000FF")))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_SMALL, data = ColorData("red", "#FF0000")))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_SMALL, data = ColorData("green", "#00FF00")))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_SMALL, data = ColorData("gray", "#808080")))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_SMALL, data = ColorData("yellow", "#FFFF00")))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_SMALL, data = ColorData("라임", "#00FF00")))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_SMALL, data = ColorData("올리브", "#808000")))
                innerWrappedDataList.add(InnerWrappedData(viewType = InnerWrappedData.VIEW_TYPE_SMALL, data = ColorData("물오리", "#008080")))
                vh.setItems(innerWrappedDataList)

                setScrollPosition(vh)
            }
        }
    }

    private fun setScrollPosition(vh: ItemViewHolder) {
        val key = getSectionID(vh.layoutPosition)
        val state = scrollStates[key]
        if (state != null) {
            vh.binding.recyclerHorizontal.layoutManager?.onRestoreInstanceState(state)
        } else {
            vh.binding.recyclerHorizontal.layoutManager?.scrollToPosition(0)
        }
    }

    // below is an inner stuff
    inner class ItemViewHolder(_binding: MainListitemBinding) : AnchorViewHolder(_binding.root) {
        val binding = _binding
        private val innerAdapter = HorizontalItemViewAdapter()

        init {
            recyclerView.apply {
                setRecycledViewPool(viewPool)
                adapter = innerAdapter
                addItemDecoration(ItemDecoration())
                setHasFixedSize(true)
            }
        }

        @Suppress("UNCHECKED_CAST")
        fun setItems(list: MutableList<InnerWrappedData>) {
            innerAdapter.innerItems = list
        }

        private inner class ItemDecoration : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildAdapterPosition(view)
                val adapter = parent.adapter as HorizontalItemViewAdapter
                val itemCount = adapter.itemCount

                outRect.left = dipToPixel(activity, if (position == 0) 20f else 12f)
                outRect.right = dipToPixel(activity, if (position == (itemCount - 1)) 20f else 0f)
            }
        }

        private inner class HorizontalItemViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            var innerItems = mutableListOf<InnerWrappedData>()
                set(value) {
                    field.clear()
                    field.addAll(value)
                    notifyItemRangeChanged(0, value.size)
                }

            override fun getItemCount(): Int {
                return innerItems.size
            }

            override fun getItemViewType(position: Int): Int {
                return innerItems[position].viewType
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return when (viewType) {
                    InnerWrappedData.VIEW_TYPE_BIG -> {
                        BigViewHolder(InnerBigItemBinding.inflate(LayoutInflater.from(activity), parent, false))
                    }
                    else -> {
                        SmallViewHolder(InnerSmallItemBinding.inflate(LayoutInflater.from(activity), parent, false))
                    }
                }
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                when (holder.itemViewType) {
                    InnerWrappedData.VIEW_TYPE_BIG -> {
                        val vh = holder as BigViewHolder
                        val data = innerItems[position].data as String

                        vh.binding.tvContent.text = data

                        vh.binding.root.setOnClickListener {
                            val bundle = Bundle().apply {
                                putString(Extra.NAME, data)
                            }
                            val navOption = NavOptions.Builder().setLaunchSingleTop(true).build()
                            Navigation.findNavController(vh.binding.root).navigate(R.id.fragment_detail, bundle, navOption)
                        }
                    }
                    InnerWrappedData.VIEW_TYPE_SMALL -> {
                        val vh = holder as SmallViewHolder
                        val data = innerItems[position].data as ColorData

                        vh.binding.tvContent.text = data.name
                        vh.binding.cvLayout.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor(data.code)))
                    }
                }
            }

            private inner class BigViewHolder(_binding: InnerBigItemBinding) : RecyclerView.ViewHolder(_binding.root) {
                val binding = _binding
            }

            private inner class SmallViewHolder(_binding: InnerSmallItemBinding) : RecyclerView.ViewHolder(_binding.root) {
                val binding = _binding
            }
        }
    }
}

data class InnerWrappedData(
    val viewType: Int = View.NO_ID,
    val data: Any? = null
) {
    companion object {
        const val VIEW_TYPE_BIG = 1
        const val VIEW_TYPE_SMALL = 2
    }
}

object Extra {
    const val NAME = "NAME"
}