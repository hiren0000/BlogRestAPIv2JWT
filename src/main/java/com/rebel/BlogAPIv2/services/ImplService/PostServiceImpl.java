package com.rebel.BlogAPIv2.services.ImplService;

import com.rebel.BlogAPIv2.enitities.Category;
import com.rebel.BlogAPIv2.enitities.Post;
import com.rebel.BlogAPIv2.enitities.User;
import com.rebel.BlogAPIv2.exceptions.ResourceNotFoundException;
import com.rebel.BlogAPIv2.payloads.PostDto;
import com.rebel.BlogAPIv2.repo.CategoryRepo;
import com.rebel.BlogAPIv2.repo.PostRepo;
import com.rebel.BlogAPIv2.repo.UserRepo;
import com.rebel.BlogAPIv2.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
    public PostDto updatePost(PostDto postDto, Integer poId)
    {
        Post post = this.postRepo
                .findById(poId).orElseThrow(() -> new ResourceNotFoundException("Post", "poId", poId));

        post.setPoImageName(postDto.getPoImageName());
        post.setPoContent(postDto.getPoContent());
        post.setPoDate(new Date());
        post.setPoTitle(postDto.getPoTitle());

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
    public List<PostDto> getAllPosts()
    {
        List<Post> posts = this.postRepo.findAll();
        List<PostDto> dtos = posts.stream().map((post) -> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
        return dtos;
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

        List<Post> posts = this.postRepo.getByCategory(category);
        List<PostDto> dtos = posts.stream().map((post) -> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        return null;
    }
}
