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

    @Override
    public PostDto updatePost(PostDto postDto, Integer poId) {
        return null;
    }

    @Override
    public void deletePost(Integer poId) {

    }

    @Override
    public List<Post> getAllPosts() {
        return null;
    }

    @Override
    public PostDto getPostById(Integer poId) {
        return null;
    }

    @Override
    public List<Post> getPostByUser(Integer userId) {
        return null;
    }

    @Override
    public List<Post> getPostByCategory(Integer categoryId) {
        return null;
    }

    @Override
    public List<Post> searchPost(String keyword) {
        return null;
    }
}
