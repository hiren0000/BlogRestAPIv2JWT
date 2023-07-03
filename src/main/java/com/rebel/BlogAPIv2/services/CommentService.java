package com.rebel.BlogAPIv2.services;

import com.rebel.BlogAPIv2.enitities.Post;
import com.rebel.BlogAPIv2.payloads.CommentDto;

import java.util.List;


public interface CommentService
{
    //Creating comment for the specific post
    CommentDto addComment(CommentDto commentDto, Integer id,  Integer poId);

    //Deleting comment
    void deleteComm(Integer coId);

    //Fetching list of comments for specific post
    List<CommentDto> getListOfCommByPost(Integer poId);
}
