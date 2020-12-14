import { NativeModules } from 'react-native';
import StripeCardInputWidget from './StripeCardInputWidget';
const { AgawebStripe } = NativeModules;

const initModule = (publishableKey) => {
  return AgawebStripe.initModule(publishableKey);
};

const confirmPaymentWithCard = (
  clientSecret,
  cardParams,
  savePaymentMethod = false
) => {
  return AgawebStripe.confirmPaymentWithCard(
    clientSecret,
    cardParams,
    !!savePaymentMethod
  );
};

const confirmPaymentWithPaymentMethodId = (clientSecret, paymentMethodId) => {
  return AgawebStripe.confirmPaymentWithPaymentMethodId(
    clientSecret,
    paymentMethodId
  );
};

const confirmSetupWithCard = (
  clientSecret,
  cardParams,
) => {
  return AgawebStripe.confirmSetupWithCard(
    clientSecret,
    cardParams
  );
};

export { StripeCardInputWidget };

export default {
  initModule,
  confirmPaymentWithCard,
  confirmPaymentWithPaymentMethodId,
  confirmSetupWithCard,
};
