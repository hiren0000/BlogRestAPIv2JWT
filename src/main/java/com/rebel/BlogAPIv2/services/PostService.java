package com.rebel.BlogAPIv2.services;

import com.rebel.BlogAPIv2.enitities.Post;
import com.rebel.BlogAPIv2.payloads.PostDto;

import java.util.List;

public interface PostService
{
    //adding post
    public PostDto addPost(PostDto postDto, Integer userid, Integer categoryId);

    //update post
    PostDto updatePost(PostDto postDto, Integer poId);

    //delete
    void deletePost(Integer poId);

    //get all posts
    List<Post> getAllPosts();

    //get single post by id
    PostDto getPostById(Integer poId);

    //get all posts by userId
    List<Post> getPostByUser(Integer userId);

    //get all posts by categoryID
    List<Post> getPostByCategory(Integer categoryId);

    //get post by searching
    List<Post> searchPost(String keyword);



}
