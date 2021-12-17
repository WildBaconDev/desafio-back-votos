package br.com.southsystem.votos.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PautaDTO {

	private Long id;
	
	private String assunto;
	
	private String corpo;
		
	private SessaoDTO sessao;
	
	private LocalDateTime dhCriacao;
}
