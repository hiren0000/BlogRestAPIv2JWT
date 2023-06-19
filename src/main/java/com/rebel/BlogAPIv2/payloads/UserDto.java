package com.rebel.BlogAPIv2.payloads;

import com.rebel.BlogAPIv2.enitities.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserDto
{
    private Integer id;

    //here I am using Hibernate Validation we should be not allowed to save null name
    @NotEmpty
    private String name;

    @Email(message = "Email address is not valid")
    private String email;

    // we can use lots of different annotation as per client's requirement will implement
    @NotEmpty
    private String pass;

    @NotEmpty
    private String about;

    private List<UserRole> roles = new ArrayList<>();
}
