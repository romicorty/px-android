package com.mercadopago.android.px.review_and_confirm.components;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.components.Component;
import com.mercadopago.android.px.components.RendererFactory;
import com.mercadopago.android.px.review_and_confirm.SummaryProvider;
import com.mercadopago.android.px.review_and_confirm.models.ReviewAndConfirmPreferences;
import com.mercadopago.android.px.review_and_confirm.models.SummaryModel;

public class SummaryComponent extends Component<SummaryComponent.SummaryProps, Void> {

    static {
        RendererFactory.register(SummaryComponent.class, SummaryRenderer.class);
    }

    private final SummaryProvider provider;

    static class SummaryProps {
        final SummaryModel summaryModel;
        final ReviewAndConfirmPreferences reviewAndConfirmPreferences;

        private SummaryProps(final SummaryModel summaryModel,
            final ReviewAndConfirmPreferences reviewAndConfirmPreferences) {
            this.summaryModel = summaryModel;
            this.reviewAndConfirmPreferences = reviewAndConfirmPreferences;
        }

        static SummaryProps createFrom(SummaryModel summaryModel,
            ReviewAndConfirmPreferences reviewAndConfirmPreferences) {
            return new SummaryProps(summaryModel, reviewAndConfirmPreferences);
        }
    }

    SummaryComponent(@NonNull final SummaryComponent.SummaryProps props,
        @NonNull final SummaryProvider provider) {
        super(props);
        this.provider = provider;
    }

    FullSummary getFullSummary() {
        return new FullSummary(props, provider);
    }

    CompactSummary getCompactSummary() {
        return new CompactSummary(props.summaryModel);
    }
}
