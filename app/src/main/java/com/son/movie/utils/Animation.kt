package com.son.movie.utils

import android.content.Context
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import com.son.movie.R

object Animation {
    fun setAnimation(context: Context): LayoutAnimationController {
        return AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_left_to_right)
    }
}