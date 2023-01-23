package com.rebel.BlogAPIv2.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto
{
    private Integer coId;
    private String coName;
    private String coDes;
}
