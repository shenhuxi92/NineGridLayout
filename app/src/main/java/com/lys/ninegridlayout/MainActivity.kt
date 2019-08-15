package com.lys.ninegridlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lys.ninegridlayout.library.NineGridAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val data: ArrayList<Any> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        data.add(0)
        data.add(0)
        data.add(0)
        nine_layout.setAdapter(Adapter())

    }

    private inner class Adapter: NineGridAdapter(data) {

        override fun setLayout() {

        }

        override fun loadImage() {

        }

    }

}
