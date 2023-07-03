package com.rebel.BlogAPIv2.controller;

import com.rebel.BlogAPIv2.payloads.ApiResponse;
import com.rebel.BlogAPIv2.payloads.CommentDto;
import com.rebel.BlogAPIv2.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CommentController
{
    @Autowired
    private CommentService service;


    // Crating new Comment
    @PostMapping("/{id}/{poId}/comments")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto, @PathVariable Integer id, @PathVariable Integer poId)
    {
        CommentDto comment = this.service.addComment(commentDto, id , poId);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    //Delete Comment
    @DeleteMapping("/comments/{coId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer coId)
    {
        this.service.deleteComm(coId);

        return new ResponseEntity<>(new ApiResponse("successfully deleted comment !!", true), HttpStatus.OK);
    }

    //Fetching list of comments for specific post
    @GetMapping("/post/{poId}/comments")
    public ResponseEntity<List<CommentDto>> getListOfComByP(@PathVariable Integer poId)
    {
        return new ResponseEntity<>(this.service.getListOfCommByPost(poId), HttpStatus.OK );
    }
}
