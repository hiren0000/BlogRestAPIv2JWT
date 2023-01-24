package com.rebel.BlogAPIv2.payloads;

import com.rebel.BlogAPIv2.enitities.Category;
import com.rebel.BlogAPIv2.enitities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDto
{
    private Integer poId;
    private String poTitle;
    private String poImageName;
    private String poContent;
    private Date poDate;

    private CategoryDto category;

    private UserDto user;



}
