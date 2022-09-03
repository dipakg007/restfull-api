package com.springboot.blog.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostDto {
	private Long id;

	@NotEmpty
	@Size(min = 2, message = "Post title should have atleast 2 characters")
	private String title;
	
	@NotEmpty
	@Size(min = 10, message = "Post description should have atleast 10 characters")
	private String description;
	
	@NotEmpty(message="content should not be null or empty")
	private String content;
	
	private Set<CommentDto> comments;

	public PostDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PostDto(Long id, String title, String description, String content) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<CommentDto> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDto> comments) {
		this.comments = comments;
	}

//	@Override
//	public String toString() {
//		return "PostDto [id=" + id + ", title=" + title + ", description=" + description + ", content=" + content + "]";
//	}

}
