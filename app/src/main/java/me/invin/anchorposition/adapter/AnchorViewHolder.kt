package me.invin.anchorposition.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import me.invin.anchorposition.R
import me.invin.anchorposition.utils.dipToPixel


open class AnchorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    companion object {
        private const val TAG = "AnchorViewHolder"
        private const val MAX_PERCENTAGE = 0.55 // 55% 이상 보이면 그쪽으로 scroll
    }

    private var state = RecyclerView.SCROLL_STATE_IDLE
    private var deltaX = 0
    var isSmallItem = true // 한화면에 아이템이 2.55개 이상 보일수 있으면 false

    protected val recyclerView: RecyclerView = view.findViewById(R.id.recycler_horizontal)

    init {
        recyclerView.apply {
            layoutManager = SmoothScrollLinearLayoutManager(view.context)
            setHasFixedSize(true)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (deltaX == 0) {
                        return
                    }
                    val oldState = state
                    state = newState
                    if (oldState == RecyclerView.SCROLL_STATE_SETTLING && newState == RecyclerView.SCROLL_STATE_IDLE
                        || oldState == RecyclerView.SCROLL_STATE_DRAGGING && newState == RecyclerView.SCROLL_STATE_IDLE
                    ) {
                        val layoutManager = recyclerView.layoutManager as SmoothScrollLinearLayoutManager
                        val firstPosition = layoutManager.findFirstVisibleItemPosition()
                        val lastPosition = layoutManager.findLastVisibleItemPosition()
                        recyclerView.getGlobalVisibleRect(Rect())

                        val visibleViewPositionSet = mutableSetOf<Int>()
                        for (i in firstPosition..lastPosition) {
                            layoutManager.findViewByPosition(i)?.let {
                                val percentage = getVisibleWidthPercentage(it)
                                if (percentage > MAX_PERCENTAGE) {
                                    visibleViewPositionSet.add(i)
                                }
                            }
                        }

                        val anchorPosition = if (isSmallItem) {
                            visibleViewPositionSet.minOrNull() ?: 0
                        } else {
                            if (deltaX > 0) { // ---->
                                visibleViewPositionSet.minOrNull() ?: 0
                            } else { // <--------
                                visibleViewPositionSet.maxOrNull() ?: 0
                            }
                        }

                        val offset = if (anchorPosition == 0) 0 else dipToPixel(context, 8f)
                        layoutManager.run {
                            this.offset = offset
                            smoothScrollToPosition(anchorPosition)
                            deltaX = 0
                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    deltaX = dx
                }

                private fun getVisibleWidthPercentage(view: View): Double {
                    val itemRect = Rect()
                    val localVisibleRect = view.getLocalVisibleRect(itemRect)
                    val visibleWidth = itemRect.width().toDouble()
                    val width = view.measuredWidth
                    val visibleWidthPercentage = visibleWidth / width
                    return if (localVisibleRect) {
                        visibleWidthPercentage
                    } else {
                        0.0
                    }
                }
            })
        }
    }
}