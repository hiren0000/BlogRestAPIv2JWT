package com.rebel.BlogAPIv2.repo;

import com.rebel.BlogAPIv2.enitities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>
{


}
