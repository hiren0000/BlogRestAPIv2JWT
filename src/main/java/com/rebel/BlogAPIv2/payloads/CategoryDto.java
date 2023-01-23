package com.rebel.BlogAPIv2.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto
{
    private Integer coId;

    @NotEmpty
    @Size(min = 3, max=8, message = "size should not be less than 3 or greater than 8")
    private String coName;

    @NotEmpty(message = "size should not be empty")
    @Size(max = 12)
    private String coDes;
}
