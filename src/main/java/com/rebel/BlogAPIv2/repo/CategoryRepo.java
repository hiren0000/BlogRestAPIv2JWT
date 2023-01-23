package com.rebel.BlogAPIv2.repo;

import com.rebel.BlogAPIv2.enitities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer>
{

}
