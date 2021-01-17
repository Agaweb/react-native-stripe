package it.agaweb.reactnativestripe

import android.app.Activity
import android.content.Intent
import com.facebook.react.bridge.*
import com.stripe.android.ApiResultCallback

import com.stripe.android.PaymentConfiguration
import com.stripe.android.PaymentIntentResult
import com.stripe.android.SetupIntentResult
import com.stripe.android.Stripe
import com.stripe.android.model.CardParams
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.ConfirmSetupIntentParams
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.model.StripeIntent

class StripeModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  private lateinit var paymentPromise: Promise
  private lateinit var setupPromise: Promise
  private var stripe: Stripe? = null
  private val activityListener = object : BaseActivityEventListener() {
    override fun onActivityResult(activity: Activity?, requestCode: Int, resultCode: Int, data: Intent?) {
      // Handle the result of stripe.confirmPayment
      stripe?.onPaymentResult(requestCode, data, object : ApiResultCallback<PaymentIntentResult> {
        override fun onSuccess(result: PaymentIntentResult) {
          val paymentIntent = result.intent
          val status = paymentIntent.status
          if (status == StripeIntent.Status.Succeeded) {
            paymentPromise.resolve(null)
          } else if (status == StripeIntent.Status.Canceled) {
            paymentPromise.reject("Stripe.Canceled", status.toString())
          } else {
            paymentPromise.reject("Stripe.OtherStatus", status.toString())
          }
        }

        override fun onError(e: Exception) {
          paymentPromise.reject("Stripe.Error", e.toString())
        }
      })

      // Handle the result of stripe.confirmSetupIntent
      stripe?.onSetupResult(requestCode, data, object : ApiResultCallback<SetupIntentResult> {
        override fun onSuccess(result: SetupIntentResult) {
          val setupIntent = result.intent
          val status = setupIntent.status
          if (status == StripeIntent.Status.Succeeded) {
            setupPromise.resolve(null)
          } else if (status == StripeIntent.Status.Canceled) {
            setupPromise.reject("Stripe.Canceled", status.toString())
          } else {
            setupPromise.reject("Stripe.OtherStatus", status.toString())
          }
        }

        override fun onError(e: Exception) {
          setupPromise.reject("Stripe.Error", e.toString())
        }
      })
    }
  }

  init {
    reactApplicationContext.addActivityEventListener(activityListener)
  }

  override fun getName(): String {
    return "AgawebStripe"
  }

  @ReactMethod
  fun initModule(publishableKey: String) {
    PaymentConfiguration.init(
      reactApplicationContext,
      publishableKey
    )
  }

  @ReactMethod
  fun confirmPaymentWithCard(clientSecret: String, cardParams: ReadableMap, savePaymentMethod: Boolean, promise: Promise) {
    val card = PaymentMethodCreateParams.createCard(CardParams(
      cardParams.getString("number")!!,
      cardParams.getInt("expMonth"),
      cardParams.getInt("expYear"),
      cardParams.getString("cvc")
    ))

    val confirmParams = ConfirmPaymentIntentParams
      .createWithPaymentMethodCreateParams(card, clientSecret, null, savePaymentMethod);

    paymentPromise = promise;
    confirmPayment(confirmParams)
  }

  @ReactMethod
  fun confirmPaymentWithPaymentMethodId(clientSecret: String, paymentMethodId: String, promise: Promise) {
    val confirmParams = ConfirmPaymentIntentParams
      .createWithPaymentMethodId(paymentMethodId, clientSecret)

    paymentPromise = promise
    confirmPayment(confirmParams)
  }

  @ReactMethod
  fun confirmCardSetup(clientSecret: String, cardParams: ReadableMap, promise: Promise) {
    val card = PaymentMethodCreateParams.createCard(CardParams(
      cardParams.getString("number")!!,
      cardParams.getInt("expMonth"),
      cardParams.getInt("expYear"),
      cardParams.getString("cvc")
    ))

    val confirmParams = ConfirmSetupIntentParams
      .create(card, clientSecret);

    setupPromise = promise;
    confirmSetupIntent(confirmParams)
  }

  private fun confirmPayment(confirmParams: ConfirmPaymentIntentParams) {
    stripe = Stripe(
      reactApplicationContext,
      PaymentConfiguration.getInstance(reactApplicationContext).publishableKey
    )
    stripe?.confirmPayment(currentActivity!!, confirmParams)
  }

  private fun confirmSetupIntent(confirmParams: ConfirmSetupIntentParams) {
    stripe = Stripe(
      reactApplicationContext,
      PaymentConfiguration.getInstance(reactApplicationContext).publishableKey
    )
    stripe?.confirmSetupIntent(currentActivity!!, confirmParams)
  }
}
