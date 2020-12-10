#import <React/RCTViewManager.h>

@interface RCT_EXTERN_MODULE(RNTStripeCardInputManager, RCTViewManager)

RCT_EXTERN_METHOD(focus:(nonnull NSNumber*) reactTag)
RCT_EXTERN_METHOD(blur:(nonnull NSNumber*) reactTag)
RCT_EXTERN_METHOD(clear:(nonnull NSNumber*) reactTag)

RCT_EXPORT_VIEW_PROPERTY(onCardValidCallback, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onFocusChange, RCTBubblingEventBlock)

RCT_EXPORT_VIEW_PROPERTY(postalCodeEntryEnabled, BOOL)
RCT_EXPORT_VIEW_PROPERTY(textColor, UIColor)
RCT_EXPORT_VIEW_PROPERTY(placeholderColor, UIColor)
RCT_EXPORT_VIEW_PROPERTY(borderColor, UIColor)
RCT_EXPORT_VIEW_PROPERTY(borderWidth, CGFloat)
RCT_REMAP_VIEW_PROPERTY(backgroundColor, cardBackgroundColor, UIColor)
RCT_EXPORT_VIEW_PROPERTY(enabled, BOOL)

@end
