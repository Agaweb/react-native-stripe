import React, {
  useLayoutEffect,
  useRef,
  forwardRef,
  useImperativeHandle,
} from 'react';
import {
  findNodeHandle,
  Platform,
  TouchableWithoutFeedback,
  UIManager,
  StyleSheet,
} from 'react-native';
import StripeCardInputWidgetNative from './nativeComponents/StripeCardInputWidgetNative';
const TextInputState = require('react-native/Libraries/Components/TextInput/TextInputState');

const StripeCardInputWidget = (props, ref) => {
  const inputRef = useRef(null);

  const _onCardValidCallback = (event) => {
    props.onCardValidCallback(event.nativeEvent);
  };

  const _onFocusChange = () => {
    TextInputState.focusInput(inputRef.current)
  };

  const focus = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(inputRef.current),
      'focus',
      []
    );
  };

  const blur = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(inputRef.current),
      'blur',
      []
    );
  };

  const clear = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(inputRef.current),
      'clear',
      []
    );
  };

  useImperativeHandle(ref, () => ({
    focus,
    blur,
    clear,
  }));

  useLayoutEffect(() => {
    const inputRefValue = inputRef.current;

    if (inputRefValue != null) {
      TextInputState.registerInput(inputRefValue);

      return () => {
        TextInputState.unregisterInput(inputRefValue);

        if (TextInputState.currentlyFocusedInput() === inputRefValue) {
          inputRefValue.blur();
        }
      };
    }
  }, [inputRef]);

  return (
    <TouchableWithoutFeedback>
      <StripeCardInputWidgetNative
	postalCodeEntryEnabled={false}
        {...props}
        style={[styles.cardInputWidget, Object.assign({}, props.style)]}
        textColor={props.cardInputStyle ? props.cardInputStyle.textColor : undefined}
        placeholderColor={props.cardInputStyle ? props.cardInputStyle.placeholderColor : undefined}
        borderColor={props.cardInputStyle ? props.cardInputStyle.borderColor : undefined}
        borderWidth={props.cardInputStyle ? props.cardInputStyle.borderWidth : undefined}
        backgroundColor={props.cardInputStyle ? props.cardInputStyle.backgroundColor : undefined}
        ref={inputRef}
        onCardValidCallback={_onCardValidCallback}
        onFocusChange={_onFocusChange}
      />
    </TouchableWithoutFeedback>
  );
};

const styles = StyleSheet.create({
  cardInputWidget: Platform.select({
    ios: {
      height: 50,
    },
    default: {},
  }),
});

const StripeCardInputWidgetWithRef = forwardRef(StripeCardInputWidget);

export default StripeCardInputWidgetWithRef;
