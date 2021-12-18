package br.com.southsystem.votos.service;

import br.com.southsystem.votos.dto.SessaoDTO;
import br.com.southsystem.votos.dto.SolicitacaoAberturaSessaoDTO;

public interface SessaoService {
	
	/**
	 * Abre a sessão de votação de uma pauta com tempo determinado na abertura, caso não esteja determinado, ficará aberto por 1 minuto.
	 * @param solicitacaoAberturaSessaoDTO
	 * @return Sessão cadastrada.
	 */
	SessaoDTO solicitarAberturaSessao(SolicitacaoAberturaSessaoDTO solicitacaoAberturaSessaoDTO);

}
