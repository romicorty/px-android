package com.mercadopago.android.px.callbacks;

import com.mercadopago.android.px.model.PaymentMethod;
import java.util.List;

public interface PaymentMethodSelectionCallback {
    void onPaymentMethodListSet(List<PaymentMethod> paymentMethodList, String bin);

    void onPaymentMethodCleared();
}
