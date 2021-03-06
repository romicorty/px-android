package com.mercadopago.android.px.util;

import com.mercadopago.android.px.model.Sites;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.mercadopago.android.px.util.TextUtils.isEmpty;

/**
 * Created by marlanti on 4/6/17.
 */

public class InstallmentsUtil {

    private static Set<String> bankInterestsNotIncludedInInstallmentsSites;

    public static boolean shouldWarnAboutBankInterests(String siteId) {
        boolean shouldWarn = false;
        if (!isEmpty(siteId) && getSitesWithBankInterestsNotIncluded().contains(siteId)) {
            shouldWarn = true;
        }
        return shouldWarn;
    }

    private static Collection<String> getSitesWithBankInterestsNotIncluded() {
        if (bankInterestsNotIncludedInInstallmentsSites == null) {
            bankInterestsNotIncludedInInstallmentsSites = new HashSet<>();
            bankInterestsNotIncludedInInstallmentsSites.add(Sites.COLOMBIA.getId());
        }
        return bankInterestsNotIncludedInInstallmentsSites;
    }
}
