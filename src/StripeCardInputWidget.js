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
        ref={inputRef}
        {...props}
        onCardValidCallback={_onCardValidCallback}
      />
    </TouchableWithoutFeedback>
  );
};

const StripeCardInputWidgetWithRef = forwardRef(StripeCardInputWidget);

export default StripeCardInputWidgetWithRef;
