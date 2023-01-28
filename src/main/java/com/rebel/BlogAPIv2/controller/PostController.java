package com.rebel.BlogAPIv2.controller;

import com.rebel.BlogAPIv2.payloads.ApiResponse;
import com.rebel.BlogAPIv2.payloads.PageResponse;
import com.rebel.BlogAPIv2.payloads.PostDto;
import com.rebel.BlogAPIv2.payloads.UserDto;
import com.rebel.BlogAPIv2.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController
{
    @Autowired
    private PostService postService;

    //adding post
    @PostMapping("/user/{uId}/category/{coId}/posts")
    public ResponseEntity<PostDto> addPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer uId, @PathVariable Integer coId)
    {
        PostDto addedPost = this.postService.addPost(postDto, uId, coId);
        return new ResponseEntity<>(addedPost, HttpStatus.CREATED);

    }

    //updating post
    @PutMapping("/{poId}")
    public ResponseEntity<PostDto> updateUser(@Valid @RequestBody PostDto postDto, @PathVariable Integer poId)
    {
        PostDto updatedDto =  this.postService.updatePost(postDto, poId);

        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    //getting the list of posts
    //pageNumber is starting from the zero by default
    @GetMapping("/posts")
    public ResponseEntity<PageResponse> getALlUsers(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                          @RequestParam (value = "pageSize", defaultValue = "5", required = false) Integer pageSize )
    {
        PageResponse list = this.postService.getAllPosts(pageNumber, pageSize);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //getting post by userId
    @GetMapping("/{poId}")
    public ResponseEntity<PostDto> getUserByuId(@PathVariable Integer poId)
    {
        PostDto postDto = this.postService.getPostById(poId);

        return new ResponseEntity<>(postDto, HttpStatus.FOUND);
    }

    //removing post by id from the Data base
    @DeleteMapping("/{poId}")
    public ResponseEntity<ApiResponse> deletebyId(@PathVariable Integer poId)
    {
        this.postService.deletePost(poId);

        return new ResponseEntity(new ApiResponse("Post is successfully deleted !! ", true), HttpStatus.OK);
    }

    //getting all the posts by specific user
    @GetMapping("/user/{uId}/posts")
    public ResponseEntity<List<PostDto>> getALlByUser(@PathVariable Integer uId, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                      @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize)
    {
        List<PostDto> posts = this.postService.getPostByUser(uId, pageNumber, pageSize);

        return new ResponseEntity<>(posts, HttpStatus.FOUND);

    }


    //getting all the posts by category
    @GetMapping("/category/{coId}/posts")
    public ResponseEntity<List<PostDto>> getALlByCateory(@PathVariable Integer coId)
    {
        List<PostDto> posts = this.postService.getPostByCategory(coId);

        return new ResponseEntity<>(posts, HttpStatus.FOUND);

    }




}
