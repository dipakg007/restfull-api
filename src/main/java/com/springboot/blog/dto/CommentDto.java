package com.springboot.blog.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CommentDto {
	private long id;
	
	@NotEmpty(message="Name should not be null or empty")
	private String name;
	
	@NotEmpty(message="Email should not be null or empty")
	@Email
	private String email;
	
	@NotEmpty(message="Body should not be null or empty")
	@Size(min = 10, message = "Comment body should have atleast 10 characters")
	private String body;

	public CommentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommentDto(long id, String name, String email, String body) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.body = body;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
