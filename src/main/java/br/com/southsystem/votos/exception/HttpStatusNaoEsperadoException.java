package br.com.southsystem.votos.exception;

public class HttpStatusNaoEsperadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public HttpStatusNaoEsperadoException(String message) {
        super(message);
    }
}
