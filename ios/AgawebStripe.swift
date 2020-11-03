import Stripe

@objc(AgawebStripe)
class AgawebStripe: NSObject, STPAuthenticationContext {
    func authenticationPresentingViewController() -> UIViewController {
        return (UIApplication.shared.delegate?.window??.rootViewController)!
    }
    
    @objc(initModule:)
    func initModule(publishableKey: String) -> Void {
        Stripe.setDefaultPublishableKey(publishableKey)
    }
    
    @objc(confirmPaymentWithCard:withCardParams:withResolver:withRejecter:)
    func confirmPaymentWithCard(clientSecret: String, cardParams: NSDictionary, resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) -> Void {
        
        let stpCardParams = STPCardParams()
        stpCardParams.number = cardParams["number"] as? String
        stpCardParams.expMonth = cardParams["expMonth"] as? UInt ?? 0
        stpCardParams.expYear = cardParams["expYear"] as? UInt ?? 0
        stpCardParams.cvc = cardParams["cvc"] as? String
        
        let paymentMethodCardParams = STPPaymentMethodCardParams.init(cardSourceParams: stpCardParams)
        let paymentMethodParams = STPPaymentMethodParams(card: paymentMethodCardParams, billingDetails: nil, metadata: nil)
        let paymentIntentParams = STPPaymentIntentParams(clientSecret: clientSecret)
        paymentIntentParams.paymentMethodParams = paymentMethodParams
        
        confirmPayment(paymentIntentParams: paymentIntentParams, resolve: resolve, reject: reject)
    }
    
    @objc(confirmPaymentWithPaymentMethodId:withPaymentMethodId:withResolver:withRejecter:)
    func confirmPaymentWithPaymentMethodId(clientSecret: String, paymentMethodId: String, resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) -> Void {
        
        let paymentIntentParams = STPPaymentIntentParams(clientSecret: clientSecret)
        paymentIntentParams.paymentMethodId = paymentMethodId
        
        confirmPayment(paymentIntentParams: paymentIntentParams, resolve: resolve, reject: reject)
    }
    
    func confirmPayment(paymentIntentParams: STPPaymentIntentParams, resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) -> Void {
        // Submit the payment
        let paymentHandler = STPPaymentHandler.shared()
        paymentHandler.confirmPayment(withParams: paymentIntentParams, authenticationContext: self) {(status, paymentIntent, error) in
            switch (status) {
            case .failed:
                reject("Stripe.Failed", error?.localizedDescription ?? "", nil)
                break
            case .canceled:
                reject("Stripe.Canceled", error?.localizedDescription ?? "", nil)
                break
            case .succeeded:
                resolve(nil);
                break
            @unknown default:
                reject("Stripe.Fatal", "Stripe fatal error", nil)
                break
            }
        }
    }
    
    @objc static func requiresMainQueueSetup() -> Bool {
        return false
    }
}
