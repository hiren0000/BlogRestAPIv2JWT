package com.rebel.BlogAPIv2.repo;

import com.rebel.BlogAPIv2.enitities.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer>
{



}
