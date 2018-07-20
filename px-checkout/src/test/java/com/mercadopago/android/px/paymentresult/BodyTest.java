package com.mercadopago.android.px.paymentresult;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.mercadopago.android.px.components.ActionDispatcher;
import com.mercadopago.android.px.components.Receipt;
import com.mercadopago.android.px.core.CheckoutStore;
import com.mercadopago.android.px.mocks.Instructions;
import com.mercadopago.android.px.mocks.PaymentResults;
import com.mercadopago.android.px.model.Instruction;
import com.mercadopago.android.px.model.Payment;
import com.mercadopago.android.px.model.PaymentResult;
import com.mercadopago.android.px.paymentresult.components.Body;
import com.mercadopago.android.px.paymentresult.components.BodyError;
import com.mercadopago.android.px.paymentresult.props.PaymentResultBodyProps;
import com.mercadopago.android.px.preferences.PaymentResultScreenPreference;
import com.mercadopago.android.px.services.constants.ProcessingModes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class BodyTest {

    private ActionDispatcher dispatcher;
    private PaymentResultProvider paymentResultProvider;

    @Before
    public void setup() {
        dispatcher = mock(ActionDispatcher.class);
        paymentResultProvider = mock(PaymentResultProvider.class);

        new PaymentResultScreenPreference.Builder().build();
    }

    @Test
    public void testBodyHasInstructions() {
        final Body body = new Body(getBodyPropsForInstructions(Instructions.getRapipagoInstruction()),
            dispatcher, paymentResultProvider);

        Assert.assertTrue(body.hasInstructions());
        Assert.assertNotNull(body.getInstructionsComponent());
    }

    @Test
    public void testInstructionsHasValidProps() {
        final Instruction instruction = Instructions.getRapipagoInstruction();
        final Body body = new Body(getBodyPropsForInstructions(instruction),
            dispatcher, paymentResultProvider);

        com.mercadopago.android.px.paymentresult.components.Instructions instructionsComponent =
            body.getInstructionsComponent();
        Assert.assertEquals(instructionsComponent.props.instruction, instruction);
    }

    private PaymentResultBodyProps getBodyPropsForInstructions(Instruction instruction) {
        return new PaymentResultBodyProps.Builder()
            .setStatus(Payment.StatusCodes.STATUS_PENDING)
            .setStatusDetail(Payment.StatusDetail.STATUS_DETAIL_PENDING_WAITING_PAYMENT)
            .setProcessingMode(ProcessingModes.AGGREGATOR)
            .setInstruction(instruction)
            .build();
    }

    @Test
    public void testBodyHasErrorWithContingency() {
        final PaymentResult paymentResult = PaymentResults.getStatusInProcessContingencyPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
            dispatcher, paymentResultProvider);

        Assert.assertTrue(body.hasBodyError());
    }

    @Test
    public void testBodyHasErrorWithRejectionOther() {
        final PaymentResult paymentResult = PaymentResults.getStatusRejectedOtherPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
            dispatcher, paymentResultProvider);

        Assert.assertTrue(body.hasBodyError());
    }

    @Test
    public void testBodyHasErrorWithRejectionInsufficientAmount() {
        final PaymentResult paymentResult = PaymentResults.getStatusRejectedInsufficientAmountPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
            dispatcher, paymentResultProvider);

        Assert.assertTrue(body.hasBodyError());
    }

    @Test
    public void testBodyHasErrorWithRejectionCallForAuth() {
        final PaymentResult paymentResult = PaymentResults.getStatusCallForAuthPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
            dispatcher, paymentResultProvider);

        Assert.assertTrue(body.hasBodyError());
    }

    @Test
    public void testBodyHasErrorWithRejectionBoleto() {
        final PaymentResult paymentResult = PaymentResults.getBoletoRejectedPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
            dispatcher, paymentResultProvider);

        Assert.assertTrue(body.hasBodyError());
    }

    @Test
    public void testBodyHasErrorWithRejectionBadFilledDate() {
        final PaymentResult paymentResult = PaymentResults.getStatusRejectedBadFilledDatePaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
            dispatcher, paymentResultProvider);

        Assert.assertFalse(body.hasBodyError());
    }

    @Test
    public void testBodyHasErrorWithRejectionBadFilledSecurityCode() {
        final PaymentResult paymentResult = PaymentResults.getStatusRejectedBadFilledSecuPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
            dispatcher, paymentResultProvider);

        Assert.assertFalse(body.hasBodyError());
    }

    @Test
    public void testBodyHasErrorWithRejectionBadFilledForm() {
        final PaymentResult paymentResult = PaymentResults.getStatusRejectedBadFilledFormPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
            dispatcher, paymentResultProvider);

        Assert.assertFalse(body.hasBodyError());
    }

    @Test
    public void testBodyErrorHasValidPropsForInsufficientData() {
        final PaymentResult paymentResult = PaymentResults.getBoletoRejectedPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
            dispatcher, paymentResultProvider);

        final BodyError bodyError = body.getBodyErrorComponent();
        Assert.assertEquals(bodyError.props.status, paymentResult.getPaymentStatus());
        Assert.assertEquals(bodyError.props.statusDetail, paymentResult.getPaymentStatusDetail());
        Assert.assertEquals(bodyError.props.paymentMethodName,
            paymentResult.getPaymentData().getPaymentMethod().getName());
    }

    @Test
    public void testBodyHasReceipt() {
        final PaymentResult paymentResult = PaymentResults.getStatusApprovedPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
            dispatcher, paymentResultProvider);

        Assert.assertTrue(body.hasReceipt());
    }

    @Test
    public void testBodyReceiptHasValidProps() {
        final PaymentResult paymentResult = PaymentResults.getStatusApprovedPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
            dispatcher, paymentResultProvider);

        Receipt receipt = body.getReceiptComponent();
        Assert.assertEquals(receipt.props.receiptId, String.valueOf(paymentResult.getPaymentId()));
    }

    @Test
    public void testBodyHasCustomTopComponent() {

        final PaymentResultScreenPreference preference = new PaymentResultScreenPreference.Builder()
            .setTopFragment(Fragment.class, new Bundle())
            .build();

        CheckoutStore.getInstance().setPaymentResultScreenPreference(preference);

        final PaymentResult paymentResult = PaymentResults.getStatusApprovedPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
            dispatcher, paymentResultProvider);

        Assert.assertTrue(body.hasTopCustomComponent());
        Assert.assertNotNull(body.hasTopCustomComponent());
    }

    @Test
    public void testBodyHasCustomBottomComponent() {
        final PaymentResultScreenPreference preference = new PaymentResultScreenPreference.Builder()
            .setBottomFragment(Fragment.class, new Bundle())
            .build();

        CheckoutStore.getInstance().setPaymentResultScreenPreference(preference);

        final PaymentResult paymentResult = PaymentResults.getStatusApprovedPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
            dispatcher, paymentResultProvider);

        Assert.assertTrue(body.hasBottomCustomComponent());
        Assert.assertNotNull(body.getPaymentMethodComponent());
    }

    @Test
    public void testBodyHasBothCustomComponent() {

        final PaymentResultScreenPreference preference = new PaymentResultScreenPreference.Builder()
            .setTopFragment(Fragment.class, new Bundle())
            .setBottomFragment(Fragment.class, new Bundle())
            .build();

        CheckoutStore.getInstance().setPaymentResultScreenPreference(preference);

        final PaymentResult paymentResult = PaymentResults.getStatusApprovedPaymentResult();
        final Body body = new Body(getBodyPropsForOnPayment(paymentResult),
            dispatcher, paymentResultProvider);


        Assert.assertTrue(body.hasTopCustomComponent());
        Assert.assertTrue(body.hasBottomCustomComponent());
    }

    private PaymentResultBodyProps getBodyPropsForOnPayment(PaymentResult paymentResult) {
        return new PaymentResultBodyProps.Builder()
            .setStatus(paymentResult.getPaymentStatus())
            .setStatusDetail(paymentResult.getPaymentStatusDetail())
            .setPaymentData(paymentResult.getPaymentData())
            .setPaymentId(paymentResult.getPaymentId())
            .build();
    }
}
