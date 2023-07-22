package com.rebel.BlogAPIv2.controller;

import com.rebel.BlogAPIv2.config.AppiConsta;
import com.rebel.BlogAPIv2.enitities.Post;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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



    //adding post
    @PostMapping("/user/{id}/category/{coId}/posts")
    public ResponseEntity<PostDto> addPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer id, @PathVariable Integer coId)
    {


        PostDto addedPost = this.postService.addPost(postDto, id, coId);
        return new ResponseEntity<>(addedPost, HttpStatus.CREATED);

    }

    //updating post and post category if we want
    @PutMapping("/category/{coId}/posts/{poId}")
    public ResponseEntity<PostDto> updateUser(@Valid @RequestBody PostDto postDto, @PathVariable Integer coId, @PathVariable Integer poId)
    {
        PostDto updatedDto =  this.postService.updatePost(postDto, coId, poId);

        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    //getting the list of posts
    //pageNumber is starting from the zero by default
    //I have also used here Constant values here so it helps us to stop using hard core values directly to our code and also avoid repetition of codes
    @GetMapping("/posts")
    public ResponseEntity<PageResponse> getALlUsers(@RequestParam(value = "pageNumber", defaultValue = AppiConsta.PAGE_NUMBER, required = false) Integer pageNumber,
                                                          @RequestParam (value = "pageSize", defaultValue = AppiConsta.PAGE_SIZE, required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy ", defaultValue = AppiConsta.SORT_BY, required = false) String sortBy,
                                                    @RequestParam(value = "sortDir ", defaultValue = AppiConsta.SORT_DIR, required = false) String sortDir)
    {
        PageResponse list = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //getting post by postId
    @GetMapping("/posts/{poId}")
    public ResponseEntity<PostDto> getUserByuId(@PathVariable Integer poId)
    {
        PostDto postDto = this.postService.getPostById(poId);

        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    //removing post by id from the Data base
    @DeleteMapping("/posts/{poId}")
    public ResponseEntity<ApiResponse> deletebyId(@PathVariable Integer poId)
    {
        this.postService.deletePost(poId);

        return new ResponseEntity(new ApiResponse("Post is successfully deleted !! ", true), HttpStatus.OK);
    }

    //getting all the posts by specific user
    @GetMapping("/user/{id}/posts")
    public ResponseEntity<List<PostDto>> getALlByUser(@PathVariable Integer id)
    {
        List<PostDto> posts = this.postService.getPostByUser(id);

        return new ResponseEntity<>(posts, HttpStatus.OK);

    }


    //getting all the posts by category
    @GetMapping("/category/{coId}/posts")
    public ResponseEntity<List<PostDto>> getALlByCategory(@PathVariable Integer coId)
    {
        List<PostDto> posts = this.postService.getPostByCategory(coId);

        return new ResponseEntity<>(posts, HttpStatus.OK);

    }


    //finding posts by searching keywords we need to put any words which in containing inside the Post title
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchByKeyword(@PathVariable ("keywords") String keyword)
    {
     List<PostDto> posts = this.postService.searchPost(keyword);

      return new ResponseEntity<>(posts, HttpStatus.OK);
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
