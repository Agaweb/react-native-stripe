#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(AgawebStripe, NSObject)

RCT_EXTERN_METHOD(initModule:(NSString *)publishableKey)

RCT_EXTERN_METHOD(confirmPaymentWithCard:(NSString *)clientSecret
                  withCardParams:(NSDictionary *)cardParams
                  withSavePaymentMethod:(nonnull NSNumber)savePaymentMethod
                  withResolver:(RCTPromiseResolveBlock)resolve
                  withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(confirmPaymentWithPaymentMethodId:(NSString *)clientSecret
                  withPaymentMethodId:(NSString *)paymentMethodId
                  withResolver:(RCTPromiseResolveBlock)resolve
                  withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(confirmCardSetup:(NSString *)clientSecret
                  withCardParams:(NSDictionary *)cardParams
                  withResolver:(RCTPromiseResolveBlock)resolve
                  withRejecter:(RCTPromiseRejectBlock)reject)

@end
