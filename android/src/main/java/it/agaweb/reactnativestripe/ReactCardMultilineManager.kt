package it.agaweb.reactnativestripe

import com.facebook.react.bridge.ReadableArray
import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.BaseViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp


class ReactCardMultilineManager : BaseViewManager<ReactCardMultilineView, ReactCardMultilineShadowNode>() {
  val REACT_CLASS = "RNTStripeCardInput"

  override fun createViewInstance(reactContext: ThemedReactContext): ReactCardMultilineView {
    return ReactCardMultilineView(reactContext)
  }

  override fun getName(): String {
    return REACT_CLASS
  }

  override fun receiveCommand(root: ReactCardMultilineView, commandId: String?, args: ReadableArray?) {
    when (commandId) {
      "focus" -> root.requestFocusFromJS()
      "blur" -> root.requestBlurFromJS()
      "clear" -> root.requestClearFromJS()
    }
  }

  @ReactProp(name = "enabled", defaultBoolean = true)
  fun setEnabled(view: ReactCardMultilineView, enabled: Boolean) {
    view.setEnabledFromJS(enabled)
  }

  @ReactProp(name = "postalCodeEntryEnabled", defaultBoolean = false)
  fun setPostalCodeEntryEnabled(view: ReactCardMultilineView, enabled: Boolean) {
    view.setPostalCodeEnabledFromJS(enabled)
  }

  override fun createShadowNodeInstance(): ReactCardMultilineShadowNode {
    return ReactCardMultilineShadowNode()
  }

  override fun getShadowNodeClass(): Class<out ReactCardMultilineShadowNode> {
    return ReactCardMultilineShadowNode::class.java
  }

  override fun updateExtraData(root: ReactCardMultilineView, extraData: Any?) {
    // do nothing
  }

  override fun getExportedCustomBubblingEventTypeConstants(): MutableMap<String, Any> {
    return MapBuilder.builder<String, Any>()
      .put("onCardValidCallback", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onCardValidCallback")))
      .put("onFocusChange", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onFocusChange")))
      .build()
  }
}
