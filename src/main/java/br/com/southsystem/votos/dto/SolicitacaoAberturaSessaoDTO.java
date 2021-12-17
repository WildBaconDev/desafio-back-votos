package br.com.southsystem.votos.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SolicitacaoAberturaSessaoDTO {

	@NotNull
	private Long idPauta;
	
	private Integer minutosSessaoAberta = 1;
	
}
