package com.rebel.BlogAPIv2.controller;

import com.rebel.BlogAPIv2.payloads.ApiResponse;
import com.rebel.BlogAPIv2.payloads.CommentDto;
import com.rebel.BlogAPIv2.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CommentController
{
    @Autowired
    private CommentService service;


    // Crating new Comment
    @PostMapping("/{id}/{poId}/comments")
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto, @PathVariable Integer id, @PathVariable Integer poId)
    {
        CommentDto comment = this.service.addComment(commentDto, id , poId);

        HttpStatus status = HttpStatus.OK;
        String message = "Comment has been added into db !! ";

        Map<String, Object> map = Map.of("comment", comment, "Status", status, "message", message);

        return ResponseEntity.ok(map);
    }

    //Delete Comment
    @DeleteMapping("/comments/{coId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer coId)
    {
        this.service.deleteComm(coId);

        HttpStatus status = HttpStatus.OK;
        String message = "Comment has been deleted from db !! ";

        Map<String, Object> map = Map.of("Status", status, "message", message);

        return ResponseEntity.ok(map);
    }

    //Fetching list of comments for specific post
    @GetMapping("/post/{poId}/comments")
    public ResponseEntity<?> getListOfComByP(@PathVariable Integer poId)
    {
        List<CommentDto> list = this.service.getListOfCommByPost(poId);

        HttpStatus status = HttpStatus.OK;
        String message = "Comments are fetching from db which belongs to specific post  !! ";

        Map<String, Object> map = Map.of("comment", list, "Status", status, "message", message);

        return ResponseEntity.ok(map);
    }
}
