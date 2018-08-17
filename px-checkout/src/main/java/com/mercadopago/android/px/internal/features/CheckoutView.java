package com.mercadopago.android.px.internal.features;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.internal.base.MvpView;
import com.mercadopago.android.px.internal.features.hooks.Hook;
import com.mercadopago.android.px.internal.viewmodel.BusinessPaymentModel;
import com.mercadopago.android.px.internal.viewmodel.OneTapModel;
import com.mercadopago.android.px.model.Card;
import com.mercadopago.android.px.model.Payment;
import com.mercadopago.android.px.model.PaymentRecovery;
import com.mercadopago.android.px.model.PaymentResult;
import com.mercadopago.android.px.model.exceptions.MercadoPagoError;

public interface CheckoutView extends MvpView {

    void showError(MercadoPagoError error);

    void showProgress();

    void showReviewAndConfirm(final boolean isUniquePaymentMethod);

    void showPaymentMethodSelection();

    void showPaymentResult(PaymentResult paymentResult);

    void backToReviewAndConfirm();

    void finishWithPaymentResult();

    void finishWithPaymentResult(Integer customResultCode);

    void finishWithPaymentResult(Payment payment);

    void finishWithPaymentResult(Integer customResultCode, Payment payment);

    void cancelCheckout();

    void cancelCheckout(MercadoPagoError mercadoPagoError);

    void cancelCheckout(Integer customResultCode, Boolean paymentMethodEdited);

    void startPaymentRecoveryFlow(PaymentRecovery paymentRecovery);

    void initializeMPTracker();

    void trackScreen();

    void showHook(final Hook hook, final int requestCode);

    void showPaymentProcessor();

    boolean isActive();

    void fetchImageFromUrl(String url);

    void showBusinessResult(BusinessPaymentModel model);

    void showOneTap(@NonNull final OneTapModel oneTapModel);

    void hideProgress();

    void exitCheckout(int resCode);

    void transitionOut();

    void showSavedCardFlow(Card card);

    void showNewCardFlow();

    void exitCheckout(int resCode, Payment createdPayment);
}
