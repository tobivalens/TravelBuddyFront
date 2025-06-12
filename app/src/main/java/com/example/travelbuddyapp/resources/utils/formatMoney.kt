package com.example.travelbuddyapp.resources.utils

fun String.formatMoney(): String {
    return this.reversed().chunked(3).joinToString(",").reversed()
}
fun Int.formatMoney(): String = this.toString().formatMoney()
