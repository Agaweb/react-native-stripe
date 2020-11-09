package it.agaweb.reactnativestripe

import android.view.View
import com.facebook.react.uimanager.LayoutShadowNode
import com.facebook.yoga.YogaMeasureFunction
import com.facebook.yoga.YogaMeasureMode
import com.facebook.yoga.YogaMeasureOutput
import com.facebook.yoga.YogaNode
import com.stripe.android.view.CardInputWidget

class ReactCardInputShadowNode : LayoutShadowNode(), YogaMeasureFunction  {
  init {
      setMeasureFunction(this)
  }

  override fun measure(node: YogaNode?, width: Float, widthMode: YogaMeasureMode?, height: Float, heightMode: YogaMeasureMode?): Long {
    val cardInputWidget = CardInputWidget(themedContext)
    val spec: Int = View.MeasureSpec.makeMeasureSpec(width.toInt(), View.MeasureSpec.EXACTLY)
    cardInputWidget.measure(spec, View.MeasureSpec.UNSPECIFIED)
    return YogaMeasureOutput.make(cardInputWidget.measuredWidth, cardInputWidget.measuredHeight)
  }

}
