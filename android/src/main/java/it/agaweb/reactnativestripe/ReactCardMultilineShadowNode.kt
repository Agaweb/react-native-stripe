package it.agaweb.reactnativestripe

import android.view.View
import com.facebook.react.uimanager.LayoutShadowNode
import com.facebook.yoga.YogaMeasureFunction
import com.facebook.yoga.YogaMeasureMode
import com.facebook.yoga.YogaMeasureOutput
import com.facebook.yoga.YogaNode
import com.stripe.android.view.CardMultilineWidget

class ReactCardMultilineShadowNode : LayoutShadowNode(), YogaMeasureFunction  {
  init {
      setMeasureFunction(this)
  }

  override fun measure(node: YogaNode?, width: Float, widthMode: YogaMeasureMode?, height: Float, heightMode: YogaMeasureMode?): Long {
    val cardMultilineWidget = CardMultilineWidget(themedContext)
    val spec: Int = View.MeasureSpec.makeMeasureSpec(width.toInt(), View.MeasureSpec.EXACTLY)
    cardMultilineWidget.measure(spec, View.MeasureSpec.UNSPECIFIED)
    return YogaMeasureOutput.make(cardMultilineWidget.measuredWidth, cardMultilineWidget.measuredHeight)
  }

}
