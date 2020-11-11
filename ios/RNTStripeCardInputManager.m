#import <React/RCTViewManager.h>

@interface RCT_EXTERN_MODULE(RNTStripeCardInputManager, RCTViewManager)

RCT_EXTERN_METHOD(focus:(nonnull NSNumber*) reactTag)
RCT_EXTERN_METHOD(blur:(nonnull NSNumber*) reactTag)
RCT_EXTERN_METHOD(clear:(nonnull NSNumber*) reactTag)

RCT_EXPORT_VIEW_PROPERTY(onCardValidCallback, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onFocusChange, RCTBubblingEventBlock)

@end
