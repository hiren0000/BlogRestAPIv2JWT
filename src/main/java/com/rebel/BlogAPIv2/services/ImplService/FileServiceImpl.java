package com.rebel.BlogAPIv2.services.ImplService;

import com.rebel.BlogAPIv2.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService
{

    //uploading images
    @Override
    public String uploadImage(String path, MultipartFile mf) {
        //File name
        String fName = mf.getOriginalFilename();
        //img.png

        //random name generate file
        String randomId = UUID.randomUUID().toString();
        String fName2 = randomId.concat(fName.substring(fName.lastIndexOf(".")));

        //Full-path
        String filePath = path + File.separator + fName2;

        //Creating folder if not created
        File f = new File(path);
        if(!f.exists())
        {
            f.mkdir();
        }

        //Copying file
        try {
            Files.copy(mf.getInputStream(), Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

        return fName2;
    }



    //Serving file on request
    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException
    {
        String fullPath = path+File.separator+fileName;

        InputStream is = new FileInputStream(fullPath);

        //DB logic

        return is;
    }
}
