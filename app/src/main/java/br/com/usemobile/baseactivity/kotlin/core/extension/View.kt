package br.com.usemobile.baseactivity.kotlin.core.extension

import android.view.View

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.setEnableAlpha(b: Boolean) {
    if (b) {
        this.isEnabled = true
//        this.setBackgroundResource(R.color.colorBlueLight)
    } else {
        this.isEnabled = false
//        this.setBackgroundResource(R.color.colorGrayMarine)
    }
}

fun View.setButtonRoundBackground(enable: Boolean) {
    if (enable) {
//        this.setBackgroundResource(R.drawable.v2_orange_round_shape)
    } else {
//        this.setBackgroundResource(R.drawable.background_gray_round_shape)
    }
}

fun View.setEnableButtonRoundBackground(enable: Boolean) {
    if (enable) {
        this.isEnabled = true
//        this.setBackgroundResource(R.drawable.v2_orange_round_shape)
    } else {
        this.isEnabled = false
//        this.setBackgroundResource(R.drawable.background_gray_round_shape)
    }
}