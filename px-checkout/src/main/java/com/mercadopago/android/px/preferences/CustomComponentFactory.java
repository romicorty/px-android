package com.mercadopago.android.px.preferences;

import android.support.annotation.NonNull;
import com.mercadopago.android.px.components.CustomComponent;

/**
 * Created by nfortuna on 1/24/18.
 */

public interface CustomComponentFactory {

    String POSIION_TOP = "position_up";
    String POSIION_BOTTOM = "position_down";

    @NonNull
    CustomComponent create(@NonNull final CustomComponent.Props props);
}
