package com.rebel.BlogAPIv2.controller;

import com.rebel.BlogAPIv2.config.AppiConsta;
import com.rebel.BlogAPIv2.enitities.ImageModel;
import com.rebel.BlogAPIv2.payloads.ApiResponse;
import com.rebel.BlogAPIv2.payloads.PageResponse;
import com.rebel.BlogAPIv2.payloads.PostDto;
import com.rebel.BlogAPIv2.services.FileService;
import com.rebel.BlogAPIv2.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class PostController
{
    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String pathDy;



    //adding post with it's relevant images
    @PostMapping(value = "/user/{id}/category/{coId}/posts", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addPost(@Valid @RequestPart("postDto") PostDto postDto, @RequestPart("imageFile") MultipartFile[] file, @PathVariable Integer id, @PathVariable Integer coId)
    {

        try
        {
            Set<ImageModel> postImages = this.uploadImage(file);
            postDto.setPostImages(postImages);
            PostDto addedPost = this.postService.addPost(postDto, id, coId);
            HttpStatus status = HttpStatus.CREATED;
            String message = "Post has been created successfully";

            Map<String, Object> map = Map.of("PostData", addedPost, "Status", status, "message", message);

            return  ResponseEntity.ok(map);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }


    }

    //Receiving Multi part file === and then fetching image details
    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException
    {
        Set<ImageModel> imageModels = new HashSet<>();

        for(MultipartFile file : multipartFiles)
        {
            ImageModel imageModel = new ImageModel(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            imageModels.add(imageModel);
        }

        return imageModels;
    }

    //updating post and post category if we want
    @PutMapping("/category/{coId}/posts/{poId}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody PostDto postDto, @PathVariable Integer coId, @PathVariable Integer poId)
    {
        PostDto updatedDto =  this.postService.updatePost(postDto, coId, poId);
        HttpStatus status = HttpStatus.OK;
        String message = "Posts has been updated successfully";

        Map<String, Object> map = Map.of("PostData", updatedDto, "Status", status, "message", message);

        return ResponseEntity.ok(map);

    }

    //getting the list of posts
    //pageNumber is starting from the zero by default
    //I have also used here Constant values here so it helps us to stop using hard core values directly to our code and also avoid repetition of codes
    @GetMapping("/posts/")
    public ResponseEntity<?> getALlUsers(@RequestParam(value = "pageNumber", defaultValue = AppiConsta.PAGE_NUMBER, required = false) Integer pageNumber,
                                                          @RequestParam (value = "pageSize", defaultValue = AppiConsta.PAGE_SIZE, required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy ", defaultValue = AppiConsta.SORT_BY, required = false) String sortBy,
                                                    @RequestParam(value = "sortDir ", defaultValue = AppiConsta.SORT_DIR, required = false) String sortDir)
    {
        PageResponse list = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);

        HttpStatus status = HttpStatus.OK;
        String message = "Posts are getting fetched";

        Map<String, Object> map = Map.of("PostData", list, "Status", status, "message", message);

        return ResponseEntity.ok(map);
    }

    //getting post by postId
    @GetMapping("/posts/{poId}")
    public ResponseEntity<?> getUserByuId(@PathVariable Integer poId)
    {
        PostDto postDto = this.postService.getPostById(poId);

        HttpStatus status = HttpStatus.OK;
        String message = "Posts are getting fetched";

        Map<String, Object> map = Map.of("PostData", postDto, "Status", status, "message", message);

        return ResponseEntity.ok(map);
    }

    //removing post by id from the Data base
    @DeleteMapping("/posts/{poId}")
    public ResponseEntity<?> deletebyId(@PathVariable Integer poId)
    {
        this.postService.deletePost(poId);
        HttpStatus status = HttpStatus.OK;
        String message = "Posts are getting fetched";

        Map<String, Object> map = Map.of("Status", status, "message", message);

        return ResponseEntity.ok(map);
    }

    //getting all the posts by specific user
    @GetMapping("/user/{id}/posts")
    public ResponseEntity<?> getALlByUser(@PathVariable Integer id)
    {
        List<PostDto> posts = this.postService.getPostByUser(id);

        HttpStatus status = HttpStatus.OK;
        String message = "Posts are getting fetched";

        Map<String, Object> map = Map.of("PostData", posts, "Status", status, "message", message);

        return ResponseEntity.ok(map);

    }


    //getting all the posts by category
    @GetMapping("/category/{coId}/posts")
    public ResponseEntity<?> getALlByCategory(@PathVariable Integer coId)
    {
        List<PostDto> posts = this.postService.getPostByCategory(coId);

        HttpStatus status = HttpStatus.OK;
        String message = "Posts are getting fetched";

        Map<String, Object> map = Map.of("PostData", posts, "Status", status, "message", message);

        return ResponseEntity.ok(map);

    }


    //finding posts by searching keywords we need to put any words which in containing inside the Post title
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<?> searchByKeyword(@PathVariable ("keywords") String keyword)
    {
     List<PostDto> posts = this.postService.searchPost(keyword);

        HttpStatus status = HttpStatus.OK;
        String message = "Posts are getting fetched";

        Map<String, Object> map = Map.of("PostData", posts, "Status", status, "message", message);

        return ResponseEntity.ok(map);
    }


    //uploading the images for specific post
    @PostMapping("/category/{coId}/post/file-upload/{poId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile mf, @PathVariable Integer coId, @PathVariable Integer poId)
    {
        PostDto postDto = this.postService.getPostById(poId);
        System.out.println("file-uploading...........");

        String fileName = this.fileService.uploadImage(pathDy, mf);

         postDto.setPoImageName(fileName);

         PostDto updatePost = this.postService.updatePost(postDto, coId, poId);

        return new ResponseEntity<>(updatePost, HttpStatus.OK);

    }

    //Fetching Image via Resources
    @GetMapping(value = "/post/image/{poImageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void FetchImage(@PathVariable String poImageName, HttpServletResponse response) throws IOException
    {
        System.out.println("Serving Images......");
        InputStream resources = this.fileService.getResource(pathDy, poImageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resources, response.getOutputStream());
    }



}
