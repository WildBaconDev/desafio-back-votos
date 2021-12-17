package br.com.southsystem.votos.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SolicitacaoPautaDTO {

	@NotBlank
	private String assunto;
	
	@NotBlank
	private String corpo;

}
