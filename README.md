# React Native Stripe

Stripe payments for react-native

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

```js
import Stripe from "@agaweb/react-native-stripe";

Stripe.initModule(YOUR_PUBLISHABLE_KEY)
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
