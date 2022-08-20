package com.springboot.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Override
	public PostDto createPost(PostDto postDto) {
		Post post = mapToEntity(postDto);
		Post newPost = postRepository.save(post);
		return mapToDto(newPost);
	}

	@Override
	public List<PostDto> getAllPosts() {
		List<Post> posts = postRepository.findAll();
		return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
	}

	@Override
	public PostDto getPostById(Long id) {
		Optional<Post> _post = Optional.ofNullable(postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id)));
		return mapToDto(_post.get());
	}
	
	@Override
	public PostDto updatePost(PostDto postDto, Long id) {
		Optional<Post> _post = Optional.ofNullable(postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id)));
		Post post = _post.get();
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		
		Post updatedPost = postRepository.save(post);
		return mapToDto(updatedPost);
	}
	
	@Override
	public void deletePost(Long id) {
		Optional<Post> _post = Optional.ofNullable(postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id)));
		postRepository.deleteById(id);
	}

	// Convert Entity To DTO
	private PostDto mapToDto(Post post) {
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setDescription(post.getDescription());
		postDto.setContent(post.getContent());

		return postDto;
	}

	// Convert DTO to Entity
	private Post mapToEntity(PostDto postDto) {
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		return post;
	}

	

	

}
