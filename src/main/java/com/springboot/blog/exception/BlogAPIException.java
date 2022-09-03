package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8684855107988678019L;
	private HttpStatus status;
	private String message;

	public BlogAPIException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public BlogAPIException(HttpStatus status, String message, String message1) {
		super(message);
		this.status = status;
		this.message = message1;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
