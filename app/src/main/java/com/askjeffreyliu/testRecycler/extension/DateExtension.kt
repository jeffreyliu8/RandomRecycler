package com.askjeffreyliu.testRecycler.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Convert date to int, for example 2019/04/30 -> 20190430
 */
fun Date.formatToInt(): Int {
    val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    return sdf.format(this).toInt()
}

/**
 * Convert date to int, for example 20190430 -> 2019/04/30
 */
fun Int.formatToDate(): Date {
    return SimpleDateFormat("yyyyMMdd", Locale.getDefault()).parse(this.toString())
}


fun Date.formatToString(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(this)
}