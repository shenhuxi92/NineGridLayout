package com.lys.ninegridlayout.library

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridSpaceDecoration: RecyclerView.ItemDecoration {

    private var mHorizontal: Int = 0
    private var mVertical: Int = 0
    private var mLeft: Int = 0
    private var mRight: Int = 0
    private var mTop: Int = 0
    private var mBottom: Int = 0
    private var mSpanCount: Int = 0
    private var mChildCount: Int = 0
    private var isFirst: Boolean = true
    private lateinit var gridLayoutManager: GridLayoutManager

    constructor(horizontal:Int,vertical: Int): this(horizontal,vertical,0,0) {
        mHorizontal = horizontal
        mVertical = vertical
    }

    constructor(horizontal:Int,vertical: Int,left: Int,right: Int): this(horizontal,vertical,left,right,0,0) {
        mHorizontal = horizontal
        mVertical = vertical
        mLeft = left
        mRight = right
    }

    constructor(horizontal:Int,vertical: Int,left: Int,right: Int,top: Int, bottom: Int): super() {
        mHorizontal = horizontal
        mVertical = vertical
        mLeft = left
        mRight = right
        mTop = top
        mBottom = bottom
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (isFirst) {
            init(parent)
            isFirst = false
        }
        if (RecyclerView.VERTICAL == gridLayoutManager.orientation) {
            handleVertical(outRect, view, parent, state)
        }else{
            handleHorizontal(outRect, view, parent, state)
        }
    }

    private fun handleHorizontal(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        val childPos = parent.getChildAdapterPosition(view)
        val sizeAvg = ((mVertical * (mSpanCount - 1) + mTop + mBottom) * 1f / mSpanCount).toInt()
        val spanSize = layoutParams.spanSize
        val spanIndex = layoutParams.spanIndex
        outRect.top = computeTop(spanIndex,sizeAvg)
        if (spanSize == 0 || spanSize == mSpanCount) {
            outRect.bottom = sizeAvg - outRect.top
        } else {
            outRect.bottom = computeBottom(spanIndex + spanSize - 1, sizeAvg)
        }
        outRect.left = mHorizontal / 2
        outRect.right = mHorizontal / 2
        if (isFirstRaw(childPos)) {
            outRect.left = mLeft
        }
        if (isLastRaw(childPos)) {
            outRect.right = mRight
        }
    }

    private fun isLastRaw(childPos: Int): Boolean {
        if (mChildCount <= 0) {
            return false
        }
        val lookup = gridLayoutManager.spanSizeLookup
        return lookup.getSpanGroupIndex(
            childPos,
            mSpanCount
        ) == lookup.getSpanGroupIndex(mChildCount - 1, mSpanCount)
    }

    private fun isFirstRaw(childPos: Int): Boolean {
        if (mChildCount <= 0) {
            return false
        }
        val lookup = gridLayoutManager.spanSizeLookup
        return lookup.getSpanGroupIndex(childPos, mSpanCount) == lookup.getSpanGroupIndex(0, mSpanCount)
    }

    private fun handleVertical(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        val childPos = parent.getChildAdapterPosition(view)
        val sizeAvg = ((mHorizontal * (mSpanCount - 1) + mLeft + mRight) * 1f / mSpanCount).toInt()
        val spanSize = layoutParams.spanSize
        val spanIndex = layoutParams.spanIndex
        outRect.left = computeLeft(spanIndex,sizeAvg)
        if (spanSize == 0 || spanSize == mSpanCount) {
            outRect.right = sizeAvg - outRect.left
        } else {
            outRect.right = computeRight(spanIndex + spanSize - 1, sizeAvg)
        }
        outRect.top = mVertical / 2
        outRect.bottom = mVertical / 2
        if (isFirstRaw(childPos)) {
            outRect.top = mTop
        }
        if (isLastRaw(childPos)) {
            outRect.bottom = mBottom
        }
    }

    private fun init(parent: RecyclerView) {
        gridLayoutManager = parent.layoutManager as? GridLayoutManager ?: throw IllegalArgumentException("LayoutManger must instance of GridLayoutManager " +
                "while using GridSpaceDecoration")
        mSpanCount = gridLayoutManager.spanCount
        mChildCount = parent.adapter!!.itemCount
    }

    /**
     * 计算Item的左边Decoration(outRect.left)尺寸,当orientation为Vertical时调用
     */
    private fun computeLeft(spanIndex: Int,sizeAvg: Int): Int {
        return when {
            spanIndex == 0 -> mLeft
            spanIndex >= mSpanCount / 2 -> {
                //从右边算起
                sizeAvg - computeRight(spanIndex, sizeAvg)
            }
            else -> {
                //从左边算起
                mHorizontal - computeRight(spanIndex - 1, sizeAvg)
            }
        }
    }

    /**
     * 计算Item的右边Decoration(outRect.right)尺寸,当orientation为Vertical时调用
     */
    private fun computeRight(spanIndex: Int, sizeAvg: Int): Int {
        return when {
            spanIndex == mSpanCount - 1 -> mRight
            spanIndex >= mSpanCount / 2 -> {
                //从右边算起
                mHorizontal - computeLeft(spanIndex + 1, sizeAvg)
            }
            else -> {
                //从左边算起
                sizeAvg - computeLeft(spanIndex, sizeAvg)
            }
        }
    }

    /**
     * 计算Item的顶部边Decoration(outRect.Top)尺寸,当orientation为Horizontal时调用
     */
    private fun computeTop(spanIndex: Int, sizeAvg: Int): Int {
        return when {
            spanIndex == 0 -> mTop
            spanIndex >= mSpanCount / 2 -> {
                //从底部算起
                sizeAvg - computeBottom(spanIndex, sizeAvg)
            }
            else -> {
                //从顶部算起
                mVertical - computeBottom(spanIndex - 1, sizeAvg)
            }
        }
    }

    /**
     * 计算Item的底部Decoration(outRect.bottom)尺寸,当orientation为Horizontal时调用
     */
    private fun computeBottom(spanIndex: Int, sizeAvg: Int): Int {
        return when {
            spanIndex == mSpanCount - 1 -> mBottom
            spanIndex >= mSpanCount / 2 -> {
                //从底部算起
                mVertical - computeTop(spanIndex + 1, sizeAvg)
            }
            else -> {
                //从顶部算起
                sizeAvg - computeTop(spanIndex, sizeAvg)
            }
        }
    }

}