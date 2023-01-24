package com.rebel.BlogAPIv2.repo;

import com.rebel.BlogAPIv2.enitities.Category;
import com.rebel.BlogAPIv2.enitities.Post;
import com.rebel.BlogAPIv2.enitities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer>
{
    List<Post> getByUser(User user);

    List<Post> getByCategory(Category category);
}
