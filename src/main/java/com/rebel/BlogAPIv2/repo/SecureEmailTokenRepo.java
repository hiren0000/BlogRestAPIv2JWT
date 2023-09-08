package com.rebel.BlogAPIv2.repo;

import com.rebel.BlogAPIv2.enitities.Email.SecureEmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecureEmailTokenRepo
{
    //fetching token
    SecureEmailToken findByToken(final String token);

    //deleteToken
    Long removeByToken(String token);
}
