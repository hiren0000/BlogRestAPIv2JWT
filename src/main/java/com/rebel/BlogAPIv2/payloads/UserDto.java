package com.rebel.BlogAPIv2.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto
{
    private Integer uId;
    private String uName;
    private String uEmail;
    private String uPass;
    private String uAbout;
}
