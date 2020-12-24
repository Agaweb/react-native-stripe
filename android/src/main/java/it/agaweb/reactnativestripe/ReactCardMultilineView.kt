package it.agaweb.reactnativestripe

import android.content.Context
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import com.facebook.infer.annotation.Assertions
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.events.RCTEventEmitter
import com.stripe.android.view.CardInputListener
import com.stripe.android.view.CardMultilineWidget


class ReactCardMultilineView(context: Context) : FrameLayout(context) {
  private val cardMultilineWidget = CardMultilineWidget(context, null, 0, false)
  private val mInputMethodManager = Assertions.assertNotNull(context.getSystemService(Context.INPUT_METHOD_SERVICE)) as InputMethodManager

  init {
    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    initMultilineWidget(cardMultilineWidget)
    addView(cardMultilineWidget)
  }

  fun setPostalCodeEnabledFromJS(enabled: Boolean) {
    cardMultilineWidget.setShouldShowPostalCode(enabled)
  }

  fun setEnabledFromJS(enabled: Boolean) {
    cardMultilineWidget.isEnabled = enabled
  }

  fun requestFocusFromJS() {
    cardMultilineWidget.requestFocus()
  }

  fun requestBlurFromJS() {
    mInputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    cardMultilineWidget.clearFocus()
  }

  fun requestClearFromJS() {
    cardMultilineWidget.clear()
  }

  private fun initMultilineWidget(cardMultilineWidget: CardMultilineWidget) {
    cardMultilineWidget.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
   cardMultilineWidget.setCardValidCallback(object: CardValidCallback {
      override fun onInputChanged(isValid: Boolean, invalidFields: Set<CardValidCallback.Fields>){
        val event: WritableMap = Arguments.createMap()
        event.putBoolean("isValid", isValid)

        if (isValid) {
          val params = Arguments.createMap()
          val typeDataParams = cardMultilineWidget.cardParams!!.typeDataParams

          params.putString("number", typeDataParams.get("number") as String)
          params.putInt("expMonth", typeDataParams.get("exp_month") as Int)
          params.putInt("expYear", typeDataParams.get("exp_year") as Int)
          params.putString("cvc", typeDataParams.get("cvc") as String)

          if (typeDataParams.get("address_zip") != null)
            params.putString("postalCode", typeDataParams.get("address_zip") as String)

          event.putMap("cardParams", params)
        }

        (context as ReactContext).getJSModule(RCTEventEmitter::class.java).receiveEvent(id, "onCardValidCallback", event)
      }

    })

    val cardInputListener = object : CardInputListener {
      override fun onCardComplete() {
        //
      }

      override fun onCvcComplete() {
        //
      }

      override fun onExpirationComplete() {
        //
      }

      override fun onFocusChange(focusField: CardInputListener.FocusField) {
        (context as ReactContext).getJSModule(RCTEventEmitter::class.java).receiveEvent(id, "onFocusChange", null)
      }
    }

    cardMultilineWidget.setCardInputListener(cardInputListener)
  }
}
