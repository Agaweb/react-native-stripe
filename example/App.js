import React, {useState} from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
} from 'react-native';

import stripe, {StripeCardInputWidget} from '@agaweb/react-native-stripe';

import configuration from './configuration.json';

stripe.initModule(configuration.publishableKey);

const App = () => {
  const [isValid, setIsValid] = useState(false);
  const [cardParams, setCardParams] = useState(undefined);

  const pay = () => {
    fetch(
      'https://api.stripe.com/v1/payment_intents?amount=1099&currency=eur',
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
            .confirmPaymentWithCard(response.client_secret, {
              number: cardParams.number,
              expMonth: cardParams.exp_month,
              expYear: cardParams.exp_year,
              cvc: cardParams.cvc,
            })
            .then(() => {
              alert('Paid');
            })
            .catch((err) => {
              alert(err);
            });
        }
      });
  };

  return (
    <View
      style={{
        flex: 1,
        justifyContent: 'center',
        paddingHorizontal: 20,
      }}>
      <View>
        <StripeCardInputWidget
          onCardValidCallback={({isValid, cardParams}) => {
            setIsValid(isValid)
            setCardParams(cardParams)
          }}
          style={{
            marginBottom: 30,
          }}
        />
      </View>
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
    </View>
  );
};

const style = StyleSheet.create({
  input: {
    borderColor: 'black',
    borderWidth: 1,
    borderRadius: 7,
    padding: 10,
    marginBottom: 20,
  },
});

export default App;
