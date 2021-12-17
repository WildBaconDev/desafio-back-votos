package br.com.southsystem.votos.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class SessaoDTO {

	private Long id;
	
	private LocalDateTime dhFechamento;
	
	private List<VotoDTO> votos;
	
	private LocalDateTime dhCriacao;
	
}
