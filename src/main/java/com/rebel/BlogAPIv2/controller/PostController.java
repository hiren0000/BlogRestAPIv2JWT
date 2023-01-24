package com.rebel.BlogAPIv2.controller;

import com.rebel.BlogAPIv2.payloads.PostDto;
import com.rebel.BlogAPIv2.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class PostController
{
    @Autowired
    private PostService postService;

    //adding post
    @PostMapping("/user/{uId}/category/{coId}/posts")
    public ResponseEntity<PostDto> addPost(@RequestBody PostDto postDto, @PathVariable Integer uId, @PathVariable Integer coId)
    {
        PostDto addedPost = this.postService.addPost(postDto, uId, coId);
        return new ResponseEntity<>(addedPost, HttpStatus.CREATED);

    }



}
