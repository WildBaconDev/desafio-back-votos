package br.com.southsystem.votos.dto;

import lombok.Data;

@Data
public class GeradorCPFDTO {

	private String status;
	private String number;
	private String number_formatted;
	private String message;
}
