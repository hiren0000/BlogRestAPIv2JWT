package com.rebel.BlogAPIv2.services;

import com.rebel.BlogAPIv2.enitities.Email.SecureEmailToken;

public interface SecureEmailTokenService
{
    //Creating new token and URl to verify register account
    SecureEmailToken createSecureToken();

    void saveSecureToken(final SecureEmailToken token);

    SecureEmailToken findByToken(final String token);

    void removeToken(final SecureEmailToken token);

    void removeTokenByToken(final String token);

}
