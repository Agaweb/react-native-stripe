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

  const focus = () => {
    if (Platform.OS === 'ios') {
      UIManager.dispatchViewManagerCommand(
        findNodeHandle(inputRef.current),
        UIManager.getViewManagerConfig('RNTStripeCardInput').Commands.focus,
        []
      );
    }
  };

  const blur = () => {
    if (Platform.OS === 'ios') {
      UIManager.dispatchViewManagerCommand(
        findNodeHandle(inputRef.current),
        UIManager.getViewManagerConfig('RNTStripeCardInput').Commands.blur,
        []
      );
    }
  };

  const clear = () => {
    if (Platform.OS === 'ios') {
      UIManager.dispatchViewManagerCommand(
        findNodeHandle(inputRef.current),
        UIManager.getViewManagerConfig('RNTStripeCardInput').Commands.clear,
        []
      );
    }
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
    <TouchableWithoutFeedback
      onPress={() => {
        TextInputState.focusInput(inputRef.current);
      }}
    >
      <StripeCardInputWidgetNative
        {...props}
        style={[styles.cardInputWidget, props.style]}
        ref={inputRef}
        onCardValidCallback={_onCardValidCallback}
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
