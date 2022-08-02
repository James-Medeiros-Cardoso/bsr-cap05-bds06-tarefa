package com.devsuperior.movieflix.services.exceptions;

//erro 401: token inválido
public class UnauthorizedException extends RuntimeException {
	static final long serialVersionUID = 1L;

	public UnauthorizedException(String msg) {
		super(msg);
	}
}
