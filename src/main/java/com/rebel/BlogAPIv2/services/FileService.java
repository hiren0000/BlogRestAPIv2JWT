package com.rebel.BlogAPIv2.services;


import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService
{
    //Uploading image
    public String uploadImage(String path, MultipartFile mf) throws IOException;

    //Fetching Images
    public InputStream getResource(String path, String fileName) throws FileNotFoundException;

}
