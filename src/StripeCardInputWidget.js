import React from 'react';
import { requireNativeComponent } from 'react-native';

const StripeCardInputWidgetNative = requireNativeComponent(
  'RCTStripeCardInputWidget'
);

const StripeCardInputWidget = (props) => {
  const _onCardValidCallback = (event) => {
    props.onCardValidCallback(event.nativeEvent);
  };

  return (
    <StripeCardInputWidgetNative
      {...props}
      onCardValidCallback={_onCardValidCallback}
    />
  );
};

export default StripeCardInputWidget;
