package com.rebel.BlogAPIv2.services;

import com.rebel.BlogAPIv2.enitities.Post;
import com.rebel.BlogAPIv2.payloads.PageResponse;
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
    PageResponse getAllPosts(Integer pageNumber, Integer pageSize);

    //get single post by id
    PostDto getPostById(Integer poId);

    //get all posts by userId
    List<PostDto> getPostByUser(Integer userId);

    //get all posts by categoryID
    List<PostDto> getPostByCategory(Integer categoryId);

    //get post by searching
    List<PostDto> searchPost(String keyword);



}
