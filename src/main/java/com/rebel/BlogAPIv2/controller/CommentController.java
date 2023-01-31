package com.rebel.BlogAPIv2.controller;

import com.rebel.BlogAPIv2.payloads.ApiResponse;
import com.rebel.BlogAPIv2.payloads.CommentDto;
import com.rebel.BlogAPIv2.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController
{
    @Autowired
    private CommentService service;

    // Crating new Comment
    @PostMapping("/posts/{poId}/comments")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto, @PathVariable Integer poId)
    {
        CommentDto comment = this.service.addComment(commentDto, poId);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    //Delete Comment
    @DeleteMapping("/comments/{coId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer coId)
    {
        this.service.deleteComm(coId);

        return new ResponseEntity<>(new ApiResponse("successfully deleted comment !!", true), HttpStatus.OK);
    }
}
