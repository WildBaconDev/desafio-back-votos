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
import lombok.NoArgsConstructor;

@Entity(name = "VOTO")
@Data
@NoArgsConstructor
public class Voto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	private LocalDateTime dhCriacao = LocalDateTime.now();

	public Voto(boolean valorVoto, Sessao sessao, Long idAssociado) {
		this.valorVoto = valorVoto;
		this.sessao = sessao;
		this.idAssociado = idAssociado;
	}
	
}
