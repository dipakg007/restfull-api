package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.dto.CommentDto;

public interface CommentService {
	CommentDto createComment(Long postId,CommentDto commentDto);
	List<CommentDto> getCommentsByPostId(Long postId);
}