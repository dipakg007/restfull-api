package com.springboot.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public CommentDto createComment(Long postId, CommentDto commentDto) {
		Comment comment = mapToEntity(commentDto);
		Optional<Post> post = Optional.ofNullable(
				postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId)));

		comment.setPost(post.get());

		Comment newComment = commentRepository.save(comment);

		return mapToDTO(newComment);
	}

	@Override
	public List<CommentDto> getCommentsByPostId(Long postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);

		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
	}

	private CommentDto mapToDTO(Comment comment) {
		CommentDto commentDto = new CommentDto();
		commentDto.setId(comment.getId());
		commentDto.setName(comment.getName());
		commentDto.setEmail(comment.getEmail());
		commentDto.setBody(comment.getBody());
		return commentDto;
	}

	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = new Comment();
		comment.setId(commentDto.getId());
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		return comment;
	}

}
