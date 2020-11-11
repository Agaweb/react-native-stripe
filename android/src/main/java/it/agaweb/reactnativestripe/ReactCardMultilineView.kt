package it.agaweb.reactnativestripe

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.events.RCTEventEmitter
import com.stripe.android.view.CardMultilineWidget

class ReactCardMultilineView(context: Context) : FrameLayout(context) {
  init {
    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val cardMultilineWidget = CardMultilineWidget(context, null, 0, false)
    initMultilineWidget(cardMultilineWidget)
    addView(cardMultilineWidget)
  }

  private fun initMultilineWidget(cardMultilineWidget: CardMultilineWidget) {
    cardMultilineWidget.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    cardMultilineWidget.setCardValidCallback { isValid, invalidFields ->
      val event: WritableMap = Arguments.createMap()
      event.putBoolean("isValid", isValid)

      if (isValid) {
        val params = Arguments.createMap()
        val typeDataParams = cardMultilineWidget.cardParams!!.typeDataParams

        params.putString("number", typeDataParams.get("number") as String)
        params.putInt("expMonth", typeDataParams.get("exp_month") as Int)
        params.putInt("expYear", typeDataParams.get("exp_year") as Int)
        params.putString("cvc", typeDataParams.get("cvc") as String)

        event.putMap("cardParams", params)
      }

      (context as ReactContext).getJSModule(RCTEventEmitter::class.java).receiveEvent(id, "onCardValidCallback", event)
    }
  }
}
