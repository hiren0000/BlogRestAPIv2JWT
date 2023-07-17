package com.rebel.BlogAPIv2.payloads;

import com.rebel.BlogAPIv2.enitities.Post;
import com.rebel.BlogAPIv2.enitities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto
{
    private Integer coId;

    private String content;

    private PostDto postDto;

    private UserDto user;

}
