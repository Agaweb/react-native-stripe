package it.agaweb.reactnativestripe

import com.facebook.react.uimanager.BaseViewManager
import com.facebook.react.uimanager.ThemedReactContext

class ReactCardInputManager : BaseViewManager<ReactCardInputView, ReactCardInputShadowNode>() {
  val REACT_CLASS = "RCTStripeCardInputWidget"

  override fun createViewInstance(reactContext: ThemedReactContext): ReactCardInputView {
    return ReactCardInputView(reactContext)
  }

  override fun getName(): String {
    return REACT_CLASS
  }

  override fun createShadowNodeInstance(): ReactCardInputShadowNode {
    return ReactCardInputShadowNode()
  }

  override fun getShadowNodeClass(): Class<out ReactCardInputShadowNode> {
    return ReactCardInputShadowNode::class.java
  }

  override fun updateExtraData(root: ReactCardInputView, extraData: Any?) {
    // do nothing
  }
}
