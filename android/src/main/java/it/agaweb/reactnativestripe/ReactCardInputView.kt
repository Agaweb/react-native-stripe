package it.agaweb.reactnativestripe

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import com.stripe.android.view.CardInputWidget

class ReactCardInputView(context: Context) : FrameLayout(context) {
  init {
    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val cardInputWidget = CardInputWidget(context)
    cardInputWidget.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    addView(cardInputWidget)
  }
}
