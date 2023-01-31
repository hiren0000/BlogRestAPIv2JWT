package com.rebel.BlogAPIv2.services.ImplService;

import com.rebel.BlogAPIv2.enitities.Comment;
import com.rebel.BlogAPIv2.enitities.Post;
import com.rebel.BlogAPIv2.exceptions.ResourceNotFoundException;
import com.rebel.BlogAPIv2.payloads.CommentDto;
import com.rebel.BlogAPIv2.repo.CommentRepo;
import com.rebel.BlogAPIv2.repo.PostRepo;
import com.rebel.BlogAPIv2.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService
{
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CommentDto addComment(CommentDto commentDto, Integer poId) {

        Post post = this.postRepo.findById(poId).orElseThrow(()-> new ResourceNotFoundException("Post","postId", poId));

        Comment comment = this.mapper.map(commentDto, Comment.class);
        comment.setPost(post);

        Comment createdComment =this.commentRepo.save(comment);

        return this.mapper.map(createdComment, CommentDto.class);
    }

    @Override
    public void deleteComm(Integer coId)
    {
        Comment comment = this.commentRepo.findById(coId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", coId));

        this.commentRepo.delete(comment);

    }
}
