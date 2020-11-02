import React, {useState} from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  TextInput,
  StyleSheet,
} from 'react-native';

import stripe from '@agaweb/react-native-stripe';

import configuration from './configuration.json';

stripe.init(configuration.publishableKey);

const App = () => {
  const [number, setNumber] = useState('4242424242424242');
  const [exp, setExp] = useState('12/22');
  const [cvc, setCvc] = useState('222');

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
          let expSplitted = exp.split('/');
          stripe
            .confirmPaymentWithCard(response.client_secret, {
              number,
              expMonth: parseInt(expSplitted[0]),
              expYear: parseInt(expSplitted[1]),
              cvc: cvc,
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
      <View style={{flexDirection: 'row'}}>
        <TextInput
          style={[
            style.input,
            {
              width: '60%',
            },
          ]}
          placeholder="Card number"
          value={number}
          onChangeText={(text) => setNumber(text)}
        />
        <TextInput
          style={[
            style.input,
            {
              flex: 1,
              marginLeft: 5,
            },
          ]}
          placeholder=""
          value={exp}
          onChangeText={(text) => setExp(text)}
        />
        <TextInput
          style={[
            style.input,
            {
              flex: 1,
              marginLeft: 5,
            },
          ]}
          placeholder=""
          value={cvc}
          onChangeText={(text) => setCvc(text)}
        />
      </View>
      <TouchableOpacity
        onPress={pay}
        style={{
          backgroundColor: 'black',
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
