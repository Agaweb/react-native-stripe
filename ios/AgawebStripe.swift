import Foundation
import Stripe

@objc(AgawebStripe)
class AgawebStripe: NSObject, STPAuthenticationContext {
    func authenticationPresentingViewController() -> UIViewController {
        return (UIApplication.shared.delegate?.window??.rootViewController)!
    }

    @objc(initModule:)
    func initModule(publishableKey: String) -> Void {
        StripeAPI.defaultPublishableKey = publishableKey
    }

    @objc(confirmPaymentWithCard:withCardParams:withSavePaymentMethod:withResolver:withRejecter:)
    func confirmPaymentWithCard(clientSecret: String,
                                cardParams: NSDictionary,
                                savePaymentMethod: NSNumber,
                                resolve: @escaping RCTPromiseResolveBlock,
                                reject: @escaping RCTPromiseRejectBlock) -> Void {
        let stpCardParams = STPCardParams()
        stpCardParams.number = cardParams["number"] as? String
        stpCardParams.expMonth = cardParams["expMonth"] as? UInt ?? 0
        stpCardParams.expYear = cardParams["expYear"] as? UInt ?? 0
        stpCardParams.cvc = cardParams["cvc"] as? String

        let paymentMethodCardParams = STPPaymentMethodCardParams.init(cardSourceParams: stpCardParams)
        let paymentMethodParams = STPPaymentMethodParams(card: paymentMethodCardParams, billingDetails: nil, metadata: nil)
        let paymentIntentParams = STPPaymentIntentParams(clientSecret: clientSecret)
        paymentIntentParams.paymentMethodParams = paymentMethodParams
        paymentIntentParams.savePaymentMethod = savePaymentMethod

        confirmPayment(paymentIntentParams: paymentIntentParams, resolve: resolve, reject: reject)
    }

    @objc(confirmPaymentWithPaymentMethodId:withPaymentMethodId:withResolver:withRejecter:)
    func confirmPaymentWithPaymentMethodId(clientSecret: String, paymentMethodId: String, resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) -> Void {

        let paymentIntentParams = STPPaymentIntentParams(clientSecret: clientSecret)
        paymentIntentParams.paymentMethodId = paymentMethodId

        confirmPayment(paymentIntentParams: paymentIntentParams, resolve: resolve, reject: reject)
    }

    @objc(confirmCardSetup:withCardParams:withResolver:withRejecter:)
    func confirmCardSetup(clientSecret: String, cardParams: NSDictionary, resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) -> Void {

        let stpCardParams = STPCardParams()
        stpCardParams.number = cardParams["number"] as? String
        stpCardParams.expMonth = cardParams["expMonth"] as? UInt ?? 0
        stpCardParams.expYear = cardParams["expYear"] as? UInt ?? 0
        stpCardParams.cvc = cardParams["cvc"] as? String

        let paymentMethodCardParams = STPPaymentMethodCardParams.init(cardSourceParams: stpCardParams)
        let paymentMethodParams = STPPaymentMethodParams(card: paymentMethodCardParams, billingDetails: nil, metadata: nil)
        let setupPaymentIntentParams = STPSetupIntentConfirmParams(clientSecret: clientSecret)
        setupPaymentIntentParams.paymentMethodParams = paymentMethodParams

        confirmSetupIntent(setupPaymentIntentParams: setupPaymentIntentParams, resolve: resolve, reject: reject)
    }

    func confirmPayment(paymentIntentParams: STPPaymentIntentParams, resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) -> Void {
        // Submit the payment
        STPPaymentHandler.shared().confirmPayment(paymentIntentParams, with: self) { (status, paymentIntent, error) in
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

    func confirmSetupIntent(setupPaymentIntentParams: STPSetupIntentConfirmParams, resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) -> Void {
        // Submit the setup
        STPPaymentHandler.shared().confirmSetupIntent(setupPaymentIntentParams, with: self) {(status, paymentIntent, error) in
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
