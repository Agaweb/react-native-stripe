import Foundation
import Stripe

class RNTStripeCardInputView: STPPaymentCardTextField, STPPaymentCardTextFieldDelegate {
    @objc
    var onCardValidCallback: RCTBubblingEventBlock?
    
    @objc
    var onFocusChange: RCTBubblingEventBlock?
    
    func defaultInitializers(){
        delegate = self
    }
    
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
            
            if(postalCodeEntryEnabled){
                cardParams["postalCode"] = textField.postalCode
            }
            
            params["cardParams"] = cardParams
        }
        
        onCardValidCallback!(params)
    }
}
