package br.com.southsystem.votos.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.southsystem.votos.dto.ApiResponseErrorDTO;

@RestControllerAdvice
public class ErrorHandlingControllerAdvice {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ApiResponseErrorDTO> handle(MethodArgumentNotValidException exception) {

		return exception.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(fieldError -> new ApiResponseErrorDTO(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList());
	}
}