package br.com.southsystem.votos.dto;

import br.com.southsystem.votos.model.StatusContabilizacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContagemVotosDTO {

	private Long qtdSim;
	private Long qtdNao;
	private StatusContabilizacao resultado;
	
	public ContagemVotosDTO(long qtdSim, long qtdNao) {
		this.qtdSim = qtdSim;
		this.qtdNao = qtdNao;
	}
	
}
