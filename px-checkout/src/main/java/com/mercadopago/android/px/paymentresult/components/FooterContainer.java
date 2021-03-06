package com.mercadopago.android.px.paymentresult.components;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import com.mercadopago.android.px.components.ActionDispatcher;
import com.mercadopago.android.px.components.Button;
import com.mercadopago.android.px.components.Component;
import com.mercadopago.android.px.components.Footer;
import com.mercadopago.android.px.components.NextAction;
import com.mercadopago.android.px.components.RecoverPaymentAction;
import com.mercadopago.android.px.components.RendererFactory;
import com.mercadopago.android.px.components.ResultCodeAction;
import com.mercadopago.android.px.core.CheckoutStore;
import com.mercadopago.android.px.model.Payment;
import com.mercadopago.android.px.model.PaymentResult;
import com.mercadopago.android.px.paymentresult.PaymentResultProvider;
import com.mercadopago.android.px.preferences.PaymentResultScreenPreference;
import com.mercadopago.android.px.review_and_confirm.components.actions.ChangePaymentMethodAction;
import com.mercadopago.android.px.util.TextUtils;

public class FooterContainer extends Component<FooterContainer.Props, Void> {

    static {
        RendererFactory.register(FooterContainer.class, FooterContainerRenderer.class);
    }

    public PaymentResultProvider resourcesProvider;

    public FooterContainer(@NonNull final Props props,
        @NonNull final ActionDispatcher dispatcher,
        @NonNull final PaymentResultProvider provider) {
        super(props, dispatcher);
        resourcesProvider = provider;
    }

    @VisibleForTesting
    Footer getFooter() {
        return new Footer(getFooterProps(), getDispatcher());
    }

    @VisibleForTesting
    Footer.Props getFooterProps() {

        final PaymentResultScreenPreference preferences = CheckoutStore.getInstance()
            .getPaymentResultScreenPreference();

        Button.Props buttonAction = null;
        Button.Props linkAction = null;

        if (props.paymentResult.isStatusApproved()) {

            if (!preferences.isCongratsSecondaryExitButtonEnabled() ||
                preferences.getSecondaryCongratsExitButtonTitle() == null
                || preferences.getSecondaryCongratsExitResultCode() == null) {
                buttonAction = null;
            } else {
                buttonAction = new Button.Props(
                    preferences.getSecondaryCongratsExitButtonTitle(),
                    new ResultCodeAction(preferences.getSecondaryCongratsExitResultCode())
                );
            }

            if (TextUtils.isEmpty(preferences.getExitButtonTitle())) {
                linkAction = new Button.Props(resourcesProvider.getContinueShopping(), new NextAction());
            } else {
                linkAction = new Button.Props(preferences.getExitButtonTitle(), new NextAction());
            }
        } else if (props.paymentResult.isStatusPending() || props.paymentResult.isStatusInProcess()) {

            if (!preferences.isPendingSecondaryExitButtonEnabled() ||
                preferences.getSecondaryPendingExitButtonTitle() == null
                || preferences.getSecondaryPendingExitResultCode() == null) {
                buttonAction = null;
            } else {
                buttonAction = new Button.Props(
                    preferences.getSecondaryPendingExitButtonTitle(),
                    new ResultCodeAction(preferences.getSecondaryPendingExitResultCode())
                );
            }

            if (TextUtils.isEmpty(preferences.getExitButtonTitle())) {
                linkAction = new Button.Props(resourcesProvider.getContinueShopping(), new NextAction());
            } else {
                linkAction = new Button.Props(preferences.getExitButtonTitle(), new NextAction());
            }
        } else if (props.paymentResult.isStatusRejected()) {

            if (Payment.StatusDetail.STATUS_DETAIL_CC_REJECTED_CALL_FOR_AUTHORIZE
                .equals(props.paymentResult.getPaymentStatusDetail())) {

                buttonAction = new Button.Props(
                    resourcesProvider.getChangePaymentMethodLabel(),
                    new ChangePaymentMethodAction()
                );

                linkAction = new Button.Props(resourcesProvider.getCancelPayment(), new NextAction());
            } else if (Payment.StatusDetail.STATUS_DETAIL_CC_REJECTED_CARD_DISABLED
                .equals(props.paymentResult.getPaymentStatusDetail())) {

                buttonAction = new Button.Props(
                    resourcesProvider.getCardEnabled(),
                    new RecoverPaymentAction()
                );

                linkAction = new Button.Props(resourcesProvider.getChangePaymentMethodLabel(),
                    new ChangePaymentMethodAction());
            } else if (Payment.StatusDetail.STATUS_DETAIL_CC_REJECTED_INSUFFICIENT_AMOUNT
                .equals(props.paymentResult.getPaymentStatusDetail())) {

                buttonAction = new Button.Props(
                    resourcesProvider.getChangePaymentMethodLabel(),
                    new ChangePaymentMethodAction()
                );

                linkAction = new Button.Props(resourcesProvider.getCancelPayment(), new NextAction());
            } else if (Payment.StatusDetail.isBadFilled(props.paymentResult.getPaymentStatusDetail())) {
                buttonAction = new Button.Props(
                    resourcesProvider.getRejectedBadFilledCardTitle(),
                    new RecoverPaymentAction()
                );

                linkAction = new Button.Props(resourcesProvider.getChangePaymentMethodLabel(),
                    new ChangePaymentMethodAction());
            } else if (Payment.StatusDetail.STATUS_DETAIL_CC_REJECTED_DUPLICATED_PAYMENT
                .equals(props.paymentResult.getPaymentStatusDetail())) {

                buttonAction = null;

                linkAction = new Button.Props(resourcesProvider.getContinueShopping(), new NextAction());
            } else {

                buttonAction = new Button.Props(
                    resourcesProvider.getChangePaymentMethodLabel(),
                    new ChangePaymentMethodAction()
                );

                linkAction = new Button.Props(resourcesProvider.getCancelPayment(), new NextAction());
            }

            // Remove the button by user preference
            if (!preferences.isRejectedSecondaryExitButtonEnabled()) {
                buttonAction = null;
            }
        }

        return new Footer.Props(
            buttonAction, linkAction
        );
    }

    public static class Props {

        public final PaymentResult paymentResult;

        public Props(PaymentResult paymentResult) {
            this.paymentResult = paymentResult;
        }
    }
}