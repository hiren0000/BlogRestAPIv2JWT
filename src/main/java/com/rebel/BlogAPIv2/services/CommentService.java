package com.rebel.BlogAPIv2.services;

import com.rebel.BlogAPIv2.payloads.CommentDto;


public interface CommentService
{
    //Creating comment for the specific post
    CommentDto addComment(CommentDto commentDto, Integer poId);

    //Deleting comment
    void deleteComm(Integer coId);
}
