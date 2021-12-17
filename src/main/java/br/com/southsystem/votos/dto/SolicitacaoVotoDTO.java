package br.com.southsystem.votos.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SolicitacaoVotoDTO {

	@NotNull
	private boolean voto;
	
	@NotNull
	private Long idAssociado;
	
	@NotNull
	private Long idPauta;
	
}
