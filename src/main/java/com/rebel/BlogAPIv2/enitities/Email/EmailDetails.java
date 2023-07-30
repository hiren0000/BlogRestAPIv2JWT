package com.rebel.BlogAPIv2.enitities.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails
{
    private String recipientEmail;
    private String msgBody;
    private String subject;
    private String attachment;
}
