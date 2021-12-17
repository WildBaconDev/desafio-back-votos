package br.com.southsystem.votos.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity(name = "VOTO")
@Data
public class Voto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "VALOR_VOTO")
	private boolean valorVoto;
	
	@ManyToOne
	@JoinColumn(name = "ID_SESSAO")
	private Sessao sessao;
	
	@Column(name = "ID_ASSOCIADO")
	private Long idAssociado;
	
	@Column(name = "DH_CRIACAO")
	private LocalDateTime dhCriacao;
	
}
