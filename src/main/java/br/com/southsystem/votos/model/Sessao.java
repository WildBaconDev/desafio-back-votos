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
import lombok.NoArgsConstructor;

@Entity(name = "SESSAO")
@Data
@NoArgsConstructor
public class Sessao {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	private LocalDateTime dhCriacao = LocalDateTime.now();

	/**
	 * Irá instanciar uma Sessão com a hora de fechamento calculada com os minutos.
	 * @param pauta
	 * @param minutos
	 */
	public Sessao(Pauta pauta, int minutos) {
		this.dhFechamento = LocalDateTime.now().plusMinutes(minutos);
		this.pauta = pauta;
	}
	
}
