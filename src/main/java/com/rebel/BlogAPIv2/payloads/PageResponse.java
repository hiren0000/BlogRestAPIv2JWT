package com.rebel.BlogAPIv2.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageResponse
{
    List<PostDto> content;
    Integer pageNumber;
    Integer pageSize;
    Long totalElements;
    Integer totalPages;
    boolean lastPage;

}
