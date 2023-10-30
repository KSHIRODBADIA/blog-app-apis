package com.kshirod.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kshirod.blog.entities.Comment;
import com.kshirod.blog.entities.Post;
import com.kshirod.blog.exceptions.ResourceNotFoundException;
import com.kshirod.blog.payloads.CommentDto;
import com.kshirod.blog.repositories.CommentRepo;
import com.kshirod.blog.repositories.PostRepo;
import com.kshirod.blog.services.CommentService;
@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment save = this.commentRepo.save(comment);
		return this.modelMapper.map(save, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Id", commentId));
		this.commentRepo.delete(com);
	}

}
