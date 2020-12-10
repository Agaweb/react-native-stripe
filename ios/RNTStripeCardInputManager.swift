import Foundation
import Stripe

@objc(RNTStripeCardInputManager)
class RNTStripeCardInputManager: RCTViewManager {
    override func view() -> UIView! {
        return RNTStripeCardInputView()
    }
    
    @objc func focus(_ reactTag: NSNumber) {
        self.bridge!.uiManager.addUIBlock { (uiManager: RCTUIManager?, viewRegistry:[NSNumber : UIView]?) in
            let view: RNTStripeCardInputView = viewRegistry![reactTag] as! RNTStripeCardInputView;
            view.focus()
          }
    }
    
    @objc func blur(_ reactTag: NSNumber) {
        self.bridge!.uiManager.addUIBlock { (uiManager: RCTUIManager?, viewRegistry:[NSNumber : UIView]?) in
            let view: RNTStripeCardInputView = viewRegistry![reactTag] as! RNTStripeCardInputView;
            view.blur()
          }
    }
    
    @objc func clear(_ reactTag: NSNumber) {
        self.bridge!.uiManager.addUIBlock { (uiManager: RCTUIManager?, viewRegistry:[NSNumber : UIView]?) in
            let view: RNTStripeCardInputView = viewRegistry![reactTag] as! RNTStripeCardInputView;
            view.clear()
          }
    }
    
    override static func requiresMainQueueSetup() -> Bool {
        return true
    }
}
