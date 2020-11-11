import Foundation
import Stripe

class RNTStripeCardInputView: STPPaymentCardTextField, STPPaymentCardTextFieldDelegate {
    func defaultInitializers(){
        postalCodeEntryEnabled = false
        textColor = UIColor.black
        delegate = self
    }
    
    @objc
    var onCardValidCallback: RCTBubblingEventBlock?
    
    @objc
    var onFocusChange: RCTBubblingEventBlock?
    
    func paymentCardTextFieldDidBeginEditing(_ textField: STPPaymentCardTextField) {
        onFocusChange!(nil)
    }
    
    func paymentCardTextFieldDidChange(_ textField: STPPaymentCardTextField){
        var params = Dictionary<String, AnyHashable>()
        params["isValid"] = textField.isValid
        
        if(textField.isValid){
            var cardParams = Dictionary<String, AnyHashable>()
            cardParams["number"] = textField.cardNumber
            cardParams["expMonth"] = textField.expirationMonth
            cardParams["expYear"] = textField.expirationYear
            cardParams["cvc"] = textField.cvc
            
            params["cardParams"] = cardParams
        }
        
        onCardValidCallback!(params)
    }
}
