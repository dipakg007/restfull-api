package com.springboot.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
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

	@Autowired
	private ModelMapper modelMapper;

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

	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {
		Comment comment = validateData(postId, commentId);
		return mapToDTO(comment);
	}

	@Override
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
		Comment comment = validateData(postId, commentId);
		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());

		Comment updatedComment = commentRepository.save(comment);

		return mapToDTO(updatedComment);
	}

	@Override
	public void deleteComment(Long postId, Long commentId) {
		validateData(postId, commentId);
		commentRepository.deleteById(commentId);

	}

	private Comment validateData(Long postId, Long commentId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (Boolean.FALSE.equals(comment.getPost().getId().equals(post.getId()))) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to this post");
		}
		return comment;
	}

	private CommentDto mapToDTO(Comment comment) {
		CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
//		CommentDto commentDto = new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());
		return commentDto;
	}

	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = modelMapper.map(commentDto, Comment.class);
//		Comment comment = new Comment();
//		comment.setId(commentDto.getId());
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());
		return comment;
	}

}
