package com.lessard.codesamples.utils;

import org.eclipse.persistence.internal.security.SecurableObjectHolder;

/**
 * Created by fdlessard on 16-08-07.
 */
public final class MiscUtils {

    private MiscUtils() {
        // Do not instantiate, this is a Utils class
    }

    public static String encryptPassword(String str) {

        SecurableObjectHolder holder = new SecurableObjectHolder();
        String encryptedPassword = holder.getSecurableObject().encryptPassword(str);

        return encryptedPassword;

    }
}
