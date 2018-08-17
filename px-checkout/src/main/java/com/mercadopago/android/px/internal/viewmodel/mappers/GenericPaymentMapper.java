package com.mercadopago.android.px.internal.viewmodel.mappers;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.model.GenericPayment;
import com.mercadopago.android.px.model.Payment;
import com.mercadopago.android.px.model.PaymentTypes;

public class GenericPaymentMapper extends Mapper<GenericPayment, Payment> {

    @Override
    public Payment map(@NonNull final GenericPayment genericPayment) {
        final Payment payment = new Payment();
        payment.setId(genericPayment.paymentId);
        payment.setPaymentMethodId(genericPayment.paymentData.getPaymentMethod().getId());
        payment.setPaymentTypeId(PaymentTypes.PLUGIN);
        payment.setStatus(genericPayment.status);
        payment.setStatusDetail(genericPayment.statusDetail);
        return payment;
    }
}
