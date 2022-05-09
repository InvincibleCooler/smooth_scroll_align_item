package me.invin.anchorposition.adapter

import android.content.Context
import android.graphics.PointF
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView


/**
 * SNAP_TO_START 로 처리하면 리스트의 아이템이 Horizontal 인 경우 왼쪽, Vertical 인 경우 위쪽으로 정렬됨.
 * 마지막까지 스크롤을 하면 마지막 아이템이 왼쪽으로 붙으려고 하나, 붙을수 없기 때문에 AnchorViewHolder 의 onScrollStateChanged 가 계속 호출되어
 * 클릭 이벤트 처리가 안됨.
 * 따라서 deltaX 를 가지고 tricky 처리함.
 */
class SmoothScrollLinearLayoutManager(private val context: Context) : LinearLayoutManager(context, HORIZONTAL, false) {
    companion object {
        private const val TAG = "SmoothScrollLinearLayoutManager"
        private const val MILLISECONDS_PER_INCH = 25f // 커질수록 anchor position까지 가는 속도가 느려짐.
    }

    var offset = 0

    override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
        val smoothScroller = Scroller(context)
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }

    private inner class Scroller(context: Context) : LinearSmoothScroller(context) {
        override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
            return this@SmoothScrollLinearLayoutManager.computeScrollVectorForPosition(targetPosition)
        }

        override fun calculateDxToMakeVisible(view: View?, snapPreference: Int): Int {
            return super.calculateDxToMakeVisible(view, SNAP_TO_START) + offset
        }

        // 필요하면 속도조절 이걸로.
        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
            return MILLISECONDS_PER_INCH / displayMetrics.densityDpi
        }
    }
}