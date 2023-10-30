package com.kshirod.blog.services;

import com.kshirod.blog.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto, Integer postId);
	void deleteComment(Integer commentId);

}
