package br.com.southsystem.votos.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity(name = "SESSAO")
@Data
public class Sessao {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "DH_FECHAMENTO")
	private LocalDateTime dhFechamento;
	
	@OneToMany(mappedBy = "sessao")
	private List<Voto> votos;
	
	@OneToOne
	@JoinColumn(name = "ID_PAUTA")
	private Pauta pauta;
	
	@Column(name = "DH_CRIACAO")
	private LocalDateTime dhCriacao;
}
