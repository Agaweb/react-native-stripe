# react-native-stripe

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
