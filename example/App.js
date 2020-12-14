import React, {useState} from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  TouchableWithoutFeedback,
  Keyboard,
} from 'react-native';

import stripe, {StripeCardInputWidget} from '@agaweb/react-native-stripe';

import configuration from './configuration.json';

stripe.initModule(configuration.publishableKey);

const App = () => {
  const [isValid, setIsValid] = useState(false);
  const [cardParams, setCardParams] = useState(undefined);

  const pay = () => {
    const customerToAppend = configuration.customerId
      ? '&customer=' + configuration.customerId
      : '';

    fetch(
      'https://api.stripe.com/v1/payment_intents?amount=1099&currency=eur' +
        customerToAppend,
      {
        method: 'POST',
        headers: {
          Authorization: 'Bearer ' + configuration.secretKey,
        },
      },
    )
      .then((response) => response.json())
      .then((response) => {
        if (response.error) {
          alert(response.error.message);
        } else if (response.client_secret) {
          stripe
            .confirmPaymentWithCard(
              response.client_secret,
              {
                number: cardParams.number,
                expMonth: cardParams.expMonth,
                expYear: cardParams.expYear,
                cvc: cardParams.cvc,
              },
              !!customerToAppend,
            )
            .then(() => {
              alert('Paid');
            })
            .catch((err) => {
              alert(err);
            });
        }
      });
  };

  const setupIntent = () => {
    if (!configuration.customerId) alert('Setup a customer id');

    fetch(
      'https://api.stripe.com/v1/setup_intents?customer=' +
        configuration.customerId,
      {
        method: 'POST',
        headers: {
          Authorization: 'Bearer ' + configuration.secretKey,
        },
      },
    )
      .then((response) => response.json())
      .then((response) => {
        if (response.error) {
          alert(response.error.message);
        } else if (response.client_secret) {
          stripe
            .confirmCardSetup(response.client_secret, {
              number: cardParams.number,
              expMonth: cardParams.expMonth,
              expYear: cardParams.expYear,
              cvc: cardParams.cvc,
            })
            .then(() => {
              alert('Successful setup');
            })
            .catch((err) => {
              alert(err);
            });
        }
      });
  };

  return (
    <TouchableWithoutFeedback
      onPress={() => {
        Keyboard.dismiss();
      }}>
      <View
        style={{
          flex: 1,
          justifyContent: 'center',
          paddingHorizontal: 20,
        }}>
        <StripeCardInputWidget
          onCardValidCallback={({isValid, cardParams}) => {
            setIsValid(isValid);
            setCardParams(cardParams);
          }}
          style={{
            marginBottom: 30,
          }}
        />
        <TouchableOpacity
          onPress={pay}
          disabled={!isValid}
          style={{
            backgroundColor: isValid ? 'black' : 'gray',
            alignItems: 'center',
            justifyContent: 'center',
            paddingVertical: 15,
            borderRadius: 10,
          }}>
          <Text
            style={{
              color: 'white',
              fontSize: 20,
            }}>
            Pay
          </Text>
        </TouchableOpacity>
        <TouchableOpacity
          onPress={setupIntent}
          disabled={!isValid}
          style={{
            backgroundColor: isValid ? 'black' : 'gray',
            alignItems: 'center',
            justifyContent: 'center',
            paddingVertical: 15,
            borderRadius: 10,
            marginTop: 10,
          }}>
          <Text
            style={{
              color: 'white',
              fontSize: 20,
            }}>
            Setup intent
          </Text>
        </TouchableOpacity>
      </View>
    </TouchableWithoutFeedback>
  );
};

export default App;
