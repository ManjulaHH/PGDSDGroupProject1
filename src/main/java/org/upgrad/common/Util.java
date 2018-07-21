package org.upgrad.common;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class Util {

    public static String hashPassword(String plainPassword){
        if(plainPassword==null || plainPassword.isEmpty())
        {
            return null;
        }
        String sha256hex = Hashing.sha256()
                .hashString(plainPassword, Charsets.US_ASCII)
                .toString();
        return sha256hex;
    }
}
