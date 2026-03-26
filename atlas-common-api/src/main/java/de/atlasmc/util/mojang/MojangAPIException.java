package de.atlasmc.util.mojang;

import java.net.http.HttpResponse;

public class MojangAPIException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final HttpResponse<String> response;
	
	public MojangAPIException(HttpResponse<String> response) {
		this(null, response);
	}
	
	public MojangAPIException(String message) {
		this(message, null);
	}
	
	public MojangAPIException(String message, HttpResponse<String> response) {
		super(message);
		this.response = response;
	}
	
	public HttpResponse<String> getResponse() {
		return response;
	}

}
