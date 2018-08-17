package com.mercadopago.android.px.internal.viewmodel.mappers;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.model.BusinessPayment;
import com.mercadopago.android.px.model.Payment;
import com.mercadopago.android.px.model.PaymentTypes;

public class BusinessPaymentMapper extends Mapper<BusinessPayment, Payment> {

    @Override
    public Payment map(@NonNull final BusinessPayment businessPayment) {
        final Payment payment = new Payment();
        //TODO missing payment id and payment method id
        payment.setPaymentTypeId(PaymentTypes.PLUGIN);
        payment.setStatus(businessPayment.getPaymentStatus());
        payment.setStatusDetail(businessPayment.getPaymentStatusDetail());
        return payment;
    }
}
