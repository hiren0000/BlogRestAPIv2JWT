package com.rebel.BlogAPIv2.payloads;

import com.rebel.BlogAPIv2.enitities.Comment;
import com.rebel.BlogAPIv2.enitities.ImageModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDto
{
    private Integer poId;

    @NotEmpty
    private String poTitle;
    private String poImageName;

    @Size(min=10, max=2500, message = "Content must no be less than 10 aor grater than 2500")
    private String poContent;
    private Date poDate;

    private Set<ImageModel> postImages;

    private CategoryDto category;

    private UserDto user;

    private Set<Comment> comments = new HashSet<>();



}
