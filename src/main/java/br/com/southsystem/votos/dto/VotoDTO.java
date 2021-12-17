package br.com.southsystem.votos.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class VotoDTO {

	private Long id;
	
	private boolean valorVoto;
	
	private Long idAssociado;
	
	private LocalDateTime dhCriacao;
}
