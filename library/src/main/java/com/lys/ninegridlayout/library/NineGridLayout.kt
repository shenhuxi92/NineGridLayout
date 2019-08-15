package com.lys.ninegridlayout.library

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NineGridLayout: FrameLayout {

    private var recycleView: RecyclerView = RecyclerView(context)
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var nineGridAdapter: NineGridAdapter

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        recycleView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    /**
     * 设置适配器
     */
    fun <T: NineGridAdapter> setAdapter(adapter: T) {
        nineGridAdapter = adapter
        //获取适配器内数据数量
        val count = nineGridAdapter.itemCount
        gridLayoutManager = when (count) {
            1 -> {
                GridLayoutManager(context,1)
            }
            2 -> {
                GridLayoutManager(context,2)
            }
            3 -> {
                GridLayoutManager(context,3)
            }
            else -> {
                GridLayoutManager(context,3)
            }
        }
        val decoration = GridSpaceDecoration(0,0,0,0)
        recycleView.addItemDecoration(decoration)
        recycleView.layoutManager = gridLayoutManager
        recycleView.adapter = nineGridAdapter
        addView(recycleView)
    }

}