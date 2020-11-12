# React Native Stripe

Stripe payments for react-native

## Warning

This library is really fresh, if you encounter a bug don't be afraid to report it

## Requirements

This module uses the latest iOS Stripe SDK (dropped support for iOS 10) so the minimum platform is now iOS 11

## Installation

```sh
npm install @agaweb/react-native-stripe
```

Since the Stripe SDK is still in Objective C, you have to add this to your project Podfile:

```
pod 'Stripe', :modular_headers => true
```

And then

```sh
cd ios && pod install
```

## A note about the widget and the manual card validation

I'll be honest I couldn't manage to get the Android CardInputWidget working, the animation doesn't work (https://github.com/stripe/stripe-android/issues/655), so I implemented the CardMultilineWidget instead. <br>
It means that Android and iOS have different UI (on Android is more material design). <br>
I could have used a third party credit card form like tipsi-stripe, but on the Stripe SDK the manual card validation is now deprecated so I'm not going to implement it.

## Usage

### Setup

You should call this method as early as possible (ex. at the first index.js), it must be called before everything else from this module

```js
import stripe from "@agaweb/react-native-stripe";

stripe.initModule(YOUR_PUBLISHABLE_KEY)
```

### Component: StripeCardInputWidget

Wrapper around the Android (CardMultilineWidget) and iOS (STPPaymentCardTextField) native card collection inputs (integrated with react-native TextInputState undocumented API, so Keyboard.dismiss() will work)

#### Basic usage
```js
import {StripeCardInputWidget} from '@agaweb/react-native-stripe';

export const Test = () => {
    return (
        <StripeCardInputWidget
            onCardValidCallback={({isValid, cardParams}) => {
                console.log(isValid, cardParams);
        }} />
    );
};
```

#### Props

| Name | Description | Details |
|-|-|-|
| `onCardValidCallback` | Callback which returs `{isValid, cardParams}` <br>- `isValid` gives you the state of the card inserted <br>- `cardParams` contains the `number, expMonth, expYear, cvc` and if enabled `postalCode` | function |
| `enabled` | Enable or disable the inputs | boolean |
| `postalCodeEntryEnabled` | Enable or disable the visibility of the postal code input | boolean |
| `cardInputStyle` <br>iOS only | Style applicable only to the ios component, support for: <br>- `textColor` <br>- `placeholderColor` <br>- `borderColor` <br>- `borderWidth` <br>- `backgroundColor` | object |

#### Methods

Use these only if really needed

| Name | Description |
|-|-|
| `focus` | Makes the native component request focus |
| `blur` | Makes the native component lose focus |
| `clear` | Clear the native fields |

### Module API: confirmPaymentWithCard

Confirm using the card details you get from the widget or from anywhere else

```js
import stripe from '@agaweb/react-native-stripe';

stripe
    .confirmPaymentWithCard(CLIENT_SECRET, {
        number: "4242424242424242",
        expMonth: 02,
        expYear: 22,
        cvc: "222",
    })
    .then(() => {
        console.log('Paid');
    })
    .catch((err) => {
        console.log(err);
    });
```

### Module API: confirmPaymentWithPaymentMethodId

Confirm using an already present paymentMethodId (ex. reuse cards)

```js
import stripe from '@agaweb/react-native-stripe';

stripe
    .confirmPaymentWithPaymentMethodId(CLIENT_SECRET, PAYMENT_METHOD_ID)
    .then(() => {
        console.log('Paid');
    })
    .catch((err) => {
        console.log(err);
    });
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
