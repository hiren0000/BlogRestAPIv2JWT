package com.rebel.BlogAPIv2.exceptions;

public class ResourceNotFoundException extends RuntimeException
{
    String ResourceName;
    String FieldName;
    Long fieldValue;

    public ResourceNotFoundException(String ResourceName, String FieldName, long fieldValue)
    {
        super(String.format("%s not found with %s : %s", ResourceName, FieldName, fieldValue));
        this.ResourceName=ResourceName;
        this.FieldName=FieldName;
        this.fieldValue=fieldValue;

    }


}
