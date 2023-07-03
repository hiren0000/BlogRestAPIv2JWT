package com.rebel.BlogAPIv2.repo;

import com.rebel.BlogAPIv2.enitities.Comment;
import com.rebel.BlogAPIv2.enitities.Post;
import com.rebel.BlogAPIv2.payloads.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer>
{

    //Fetching list of comments for specific post
    public List<Comment> findByPost(Post post);

}
