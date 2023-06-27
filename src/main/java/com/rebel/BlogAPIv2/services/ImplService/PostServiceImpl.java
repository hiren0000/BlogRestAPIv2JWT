package com.rebel.BlogAPIv2.services.ImplService;

import com.rebel.BlogAPIv2.enitities.Category;
import com.rebel.BlogAPIv2.enitities.Post;
import com.rebel.BlogAPIv2.enitities.User;
import com.rebel.BlogAPIv2.exceptions.ResourceNotFoundException;
import com.rebel.BlogAPIv2.payloads.PageResponse;
import com.rebel.BlogAPIv2.payloads.PostDto;
import com.rebel.BlogAPIv2.repo.CategoryRepo;
import com.rebel.BlogAPIv2.repo.PostRepo;
import com.rebel.BlogAPIv2.repo.UserRepo;
import com.rebel.BlogAPIv2.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService
{
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper mapper;

    //adding New Post
    @Override
    public PostDto addPost(PostDto postDto, Integer userid, Integer categoryId)
    {
        User user = this.userRepo.findById(userid).orElseThrow(() -> new ResourceNotFoundException("User", "userId",userid));

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Post post = this.mapper.map(postDto, Post.class);
        post.setCategory(category);
        post.setUser(user);
        post.setPoDate(new Date());
        post.setPoImageName("profile1.jpg");

        Post addedPost = this.postRepo.save(post);

        return this.mapper.map(addedPost, PostDto.class);
    }

    //update post
    @Override
    public PostDto updatePost(PostDto postDto, Integer coId, Integer poId)
    {
        Post post = this.postRepo
                .findById(poId).orElseThrow(() -> new ResourceNotFoundException("Post", "poId", poId));

        Category category = this.categoryRepo.findById(coId).orElseThrow(() -> new ResourceNotFoundException("Category", "coId", coId));

        post.setPoImageName(postDto.getPoImageName());
        post.setPoContent(postDto.getPoContent());
        post.setPoDate(new Date());
        post.setPoTitle(postDto.getPoTitle());
        post.setCategory(category);


        Post updatedPost = this.postRepo.save(post);

        return this.mapper.map(updatedPost, PostDto.class);
    }

    //delete post
    @Override
    public void deletePost(Integer poId)
    {
        Post post = this.postRepo
                .findById(poId).orElseThrow(() -> new ResourceNotFoundException("Post", "poId", poId));

        this.postRepo.delete(post);
    }



    //get all posts
    @Override
    public PageResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir)
    {
        //by default it is ascending order bt we can change the direction dynamically
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc"))
        {
           sort = Sort.by(sortBy).ascending();
        }else
        {
           sort = Sort.by(sortBy).descending();
        }

        //we are trying to add the pagination and also added sort object so we can perform the sorting operations
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

         Page<Post> page = this.postRepo.findAll(pageable);
         //getting all the posts on page
        List<Post> posts = page.getContent();

        List<PostDto> dtos = posts.stream().map((post) -> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());

        PageResponse pageResponse = new PageResponse();
        pageResponse.setContent(dtos);
        pageResponse.setPageNumber(page.getNumber());
        pageResponse.setPageSize(page.getSize());
        pageResponse.setTotalElements(page.getTotalElements());
        pageResponse.setTotalPages(page.getTotalPages());
        pageResponse.setLastPage(page.isLast());


        return pageResponse;
    }

    // get single post by id
    @Override
    public PostDto getPostById(Integer poId)
    {
        Post post = this.postRepo
                .findById(poId).orElseThrow(() -> new ResourceNotFoundException("Post", "poId", poId));

        return this.mapper.map(post, PostDto.class);
    }



    //getting all the post by specific user
    @Override
    public List<PostDto> getPostByUser(Integer userId)
    {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        //we are trying to add the pagination
        //Pageable pageable = PageRequest.of(pageNumber, pageSize);

        //Page<Post> page = this.postRepo.findAll(pageable);
        //getting all the posts on page
        List<Post> posts = this.postRepo.getByUser(user);

        List<PostDto> dtos = posts.stream().map((post) -> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
        return dtos;
    }


    //getting all the posts by specific category
    @Override
    public List<PostDto> getPostByCategory(Integer categoryId)
    {

        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        //we are trying to add the pagination
        //Pageable pageable = PageRequest.of(pageNumber, pageSize);

        //Page<Post> page = this.postRepo.findAll(pageable);
        //getting all the posts on page
        List<Post> posts = this.postRepo.getByCategory(category);


        List<PostDto> dtos = posts.stream().map((post) -> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
        return dtos;
    }

    //trying to find the posts by searching keywords we have to use custom method in the post repo to implement this function
    @Override
    public List<PostDto> searchPost(String keyword)
    {
        List<Post> posts =this.postRepo.findByPoTitleContaining(keyword);
        List<PostDto> dtos =posts.stream().map(post -> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());

        return dtos;
    }
}
