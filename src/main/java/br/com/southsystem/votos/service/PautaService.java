package br.com.southsystem.votos.service;

import br.com.southsystem.votos.dto.PautaDTO;
import br.com.southsystem.votos.dto.SolicitacaoPautaDTO;

public interface PautaService {

	/**
	 * Cadastra uma nova pauta.
	 * @param solicitacaoPautaDTO
	 * @return pauta cadastrada
	 */
	PautaDTO cadastrarPauta(SolicitacaoPautaDTO solicitacaoPautaDTO);
	
}
