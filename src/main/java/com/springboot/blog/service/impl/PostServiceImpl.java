package com.springboot.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto) {
		Post post = mapToEntity(postDto);
		Post newPost = postRepository.save(post);
		return mapToDto(newPost);
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		// Create pageable instance

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);

		Page<Post> posts = postRepository.findAll(pageable);

		// get content for page object
		List<Post> listOfPosts = posts.getContent();

		List<PostDto> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());

		return postResponse;
	}

	@Override
	public PostDto getPostById(Long id) {
		Optional<Post> _post = Optional.ofNullable(
				postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id)));
		return mapToDto(_post.get());
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long id) {
		Optional<Post> _post = Optional.ofNullable(
				postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id)));
		Post post = _post.get();

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());

		Post updatedPost = postRepository.save(post);
		return mapToDto(updatedPost);
	}

	@Override
	public void deletePost(Long id) {
		Optional.ofNullable(
				postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id)));
		postRepository.deleteById(id);
	}

	// Convert Entity To DTO
	private PostDto mapToDto(Post post) {
		PostDto postDto = modelMapper.map(post, PostDto.class);

//		PostDto postDto = new PostDto();
//		postDto.setId(post.getId());
//		postDto.setTitle(post.getTitle());
//		postDto.setDescription(post.getDescription());
//		postDto.setContent(post.getContent());

		return postDto;
	}

	// Convert DTO to Entity
	private Post mapToEntity(PostDto postDto) {
		Post post = modelMapper.map(postDto, Post.class);
//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
		return post;
	}

}
