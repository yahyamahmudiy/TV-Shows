package com.example.imperative.utils

import com.example.imperative.R
import java.util.*
import kotlin.collections.ArrayList

object RandomColor {

    fun randomColor(): Int {
        val random = Random()
        val colorList = ArrayList<Int>()

        colorList.add(R.color.purple_200)
        colorList.add(R.color.teal_200)
        colorList.add(android.R.color.holo_orange_light)
        colorList.add(android.R.color.system_accent2_200)

        return colorList[random.nextInt(colorList.size)]
    }
}