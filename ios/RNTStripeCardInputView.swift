import Foundation
import Stripe

class RNTStripeCardInputView: UIView, STPPaymentCardTextFieldDelegate {
    let cardTextField = STPPaymentCardTextField()
    
    @objc var onCardValidCallback: RCTBubblingEventBlock?
    
    @objc var onFocusChange: RCTBubblingEventBlock?
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        cardTextField.frame = self.bounds
        cardTextField.autoresizingMask = [.flexibleHeight, .flexibleWidth]
        cardTextField.delegate = self
        self.addSubview(cardTextField)
    }

    required init(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
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
            cardParams["brand"] = STPCardBrandUtilities.stringFrom(STPCardValidator.brand(forNumber: textField.cardNumber!))
            
            if(textField.postalCode != nil){
                cardParams["postalCode"] = textField.postalCode
            }
            
            params["cardParams"] = cardParams
        }
        
        onCardValidCallback!(params)
    }
    
    @objc func setNumberPlaceholder(_ placeholder: String){
        cardTextField.numberPlaceholder = placeholder
    }
    
    @objc func setCvcPlaceholder(_ placeholder: String){
        cardTextField.cvcPlaceholder = placeholder
    }
    
    @objc func setExpirationPlaceholder(_ placeholder: String){
        cardTextField.expirationPlaceholder = placeholder
    }
    
    @objc func setPostalCodePlaceholder(_ placeholder: String){
        cardTextField.postalCodePlaceholder = placeholder
    }
    
    @objc func setPostalCodeEntryEnabled(_ enabled: Bool){
        cardTextField.postalCodeEntryEnabled = enabled
    }
    
    @objc func setTextColor(_ color: UIColor){
        cardTextField.textColor = color
    }
    
    @objc func setPlaceholderColor(_ color: UIColor){
        cardTextField.placeholderColor = color
    }
    
    @objc func setBorderColor(_ color: UIColor){
        cardTextField.borderColor = color
    }
    
    @objc func setBorderWidth(_ width: CGFloat){
        cardTextField.borderWidth = width
    }
    
    @objc func setCardBackgroundColor(_ color: UIColor){
        cardTextField.backgroundColor = color
    }
    
    @objc func setEnabled(_ enabled: Bool){
        cardTextField.isEnabled = enabled
    }
    
    func focus(){
        cardTextField.becomeFirstResponder()
    }
    
    func blur(){
        cardTextField.resignFirstResponder()
    }
    
    func clear(){
        cardTextField.clear()
    }
}
