package br.com.southsystem.votos.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "PAUTA")
@Data
@NoArgsConstructor
public class Pauta {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "ASSUNTO")
	private String assunto;
	
	@Column(name = "CORPO")
	private String corpo;
		
	@OneToOne(mappedBy = "pauta")
	private Sessao sessao;
	
	@Column(name = "DH_CRIACAO")
	private LocalDateTime dhCriacao = LocalDateTime.now();
	
	@Column(name = "ST_CONTABILIZACAO")
	@Enumerated(EnumType.STRING)
	private StatusContabilizacao statusContabilizacao = StatusContabilizacao.NAO_CONTABILIZADO;

	public Pauta(String assunto, String corpo) {
		this.assunto = assunto;
		this.corpo = corpo;
	}
	
}
