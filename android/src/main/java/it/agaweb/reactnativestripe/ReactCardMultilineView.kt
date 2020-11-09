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
        cardMultilineWidget.cardParams!!.typeDataParams.forEach {
          if (it.value is String) {
            params.putString(it.key, it.value as String)
          }

          if (it.value is Boolean) {
            params.putBoolean(it.key, it.value as Boolean)
          }

          if (it.value is Int) {
            params.putInt(it.key, it.value as Int)
          }

          if (it.value is Double) {
            params.putDouble(it.key, it.value as Double)
          }
        }

        event.putMap("cardParams", params)
      } else {
        val fields = Arguments.createArray()

        invalidFields.forEach {
          fields.pushString(it.name)
        }

        event.putArray("invalidFields", fields)
      }

      (context as ReactContext).getJSModule(RCTEventEmitter::class.java).receiveEvent(id, "onCardValidCallback", event)
    }
  }
}
