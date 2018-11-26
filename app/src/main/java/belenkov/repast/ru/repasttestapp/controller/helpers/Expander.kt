package belenkov.repast.ru.repasttestapp.controller.helpers

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator


class Expander {
    companion object {
        fun expand(v: View, duration: Int) {
            val expand = v.visibility != View.VISIBLE

            val prevHeight = v.height
            var height = 0
            if (expand) {
                val measureSpecParams = View.MeasureSpec.getSize(View.MeasureSpec.UNSPECIFIED)
                v.measure(measureSpecParams, measureSpecParams)
                height = v.measuredHeight
            }

            val valueAnimator = ValueAnimator.ofInt(prevHeight, height)
            valueAnimator.addUpdateListener { animation ->
                v.layoutParams.height = animation.animatedValue as Int
                v.requestLayout()
            }

            valueAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    if (expand) {
                        v.visibility = View.VISIBLE
                    }
                }

                override fun onAnimationEnd(animation: Animator) {
                    if (!expand) {
                        v.visibility = View.INVISIBLE
                    }
                }

                override fun onAnimationCancel(animation: Animator) {

                }

                override fun onAnimationRepeat(animation: Animator) {

                }
            })
            valueAnimator.interpolator = DecelerateInterpolator()
            valueAnimator.duration = duration.toLong()
            valueAnimator.start()
        }
    }
}