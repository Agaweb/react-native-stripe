package it.agaweb.reactnativestripe

import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.BaseViewManager
import com.facebook.react.uimanager.ThemedReactContext

class ReactCardMultilineManager : BaseViewManager<ReactCardMultilineView, ReactCardMultilineShadowNode>() {
  val REACT_CLASS = "RCTStripeCardInputWidget"

  override fun createViewInstance(reactContext: ThemedReactContext): ReactCardMultilineView {
    return ReactCardMultilineView(reactContext)
  }

  override fun getName(): String {
    return REACT_CLASS
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
      .build()
  }
}
