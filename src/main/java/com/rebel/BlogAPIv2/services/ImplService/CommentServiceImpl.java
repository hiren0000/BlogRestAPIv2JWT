package com.rebel.BlogAPIv2.services.ImplService;

import com.rebel.BlogAPIv2.enitities.Comment;
import com.rebel.BlogAPIv2.enitities.Post;
import com.rebel.BlogAPIv2.enitities.User;
import com.rebel.BlogAPIv2.exceptions.ResourceNotFoundException;
import com.rebel.BlogAPIv2.payloads.CommentDto;
import com.rebel.BlogAPIv2.repo.CommentRepo;
import com.rebel.BlogAPIv2.repo.PostRepo;
import com.rebel.BlogAPIv2.repo.UserRepo;
import com.rebel.BlogAPIv2.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService
{
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;


    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper mapper;

    //Adding new comment by user under post
    @Override
    public CommentDto addComment(CommentDto commentDto, Integer id, Integer poId)
    {

        User user = this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "id", id));

        Post post = this.postRepo.findById(poId).orElseThrow(()-> new ResourceNotFoundException("Post","postId", poId));

        Comment comment = this.mapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);

        Comment createdComment =this.commentRepo.save(comment);

        return this.mapper.map(createdComment, CommentDto.class);
    }


    //Delete comment
    @Override
    public void deleteComm(Integer coId)
    {
        Comment comment = this.commentRepo.findById(coId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", coId));

        this.commentRepo.delete(comment);

    }


    //Fetching list of comments for specific post
    @Override
    public List<CommentDto> getListOfCommByPost(Integer poId)
    {
        Post post = this.postRepo.findById(poId).orElseThrow(()-> new ResourceNotFoundException("Post","postId", poId));

        List<Comment> comments = this.commentRepo.findByPost(post);

        List<CommentDto> dto = comments.stream().map(comment -> this.mapper.map(comment, CommentDto.class)).collect(Collectors.toList());

        return dto ;
    }



}
