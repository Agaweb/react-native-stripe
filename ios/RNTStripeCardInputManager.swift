import Foundation
import Stripe

@objc(RNTStripeCardInputManager)
class RNTStripeCardInputManager: RCTViewManager {
    override func view() -> UIView! {
        let cardTextField = RNTStripeCardInputView()
        cardTextField.defaultInitializers()
        return cardTextField
    }
    
    @objc(focus:)
    func focus(reactTag: NSNumber) {
        self.bridge!.uiManager.addUIBlock { (uiManager: RCTUIManager?, viewRegistry:[NSNumber : UIView]?) in
            let view: STPPaymentCardTextField = viewRegistry![reactTag] as! STPPaymentCardTextField;
            view.becomeFirstResponder()
          }
    }
    
    @objc(blur:)
    func blur(reactTag: NSNumber) {
        self.bridge!.uiManager.addUIBlock { (uiManager: RCTUIManager?, viewRegistry:[NSNumber : UIView]?) in
            let view: STPPaymentCardTextField = viewRegistry![reactTag] as! STPPaymentCardTextField;
            view.resignFirstResponder()
          }
    }
    
    @objc(clear:)
    func clear(reactTag: NSNumber) {
        self.bridge!.uiManager.addUIBlock { (uiManager: RCTUIManager?, viewRegistry:[NSNumber : UIView]?) in
            let view: STPPaymentCardTextField = viewRegistry![reactTag] as! STPPaymentCardTextField;
            view.clear()
          }
    }
    
    override static func requiresMainQueueSetup() -> Bool {
        return true
    }
}
