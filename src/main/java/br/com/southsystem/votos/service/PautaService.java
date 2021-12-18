package br.com.southsystem.votos.service;

import java.util.Optional;

import br.com.southsystem.votos.dto.PautaDTO;
import br.com.southsystem.votos.dto.SolicitacaoPautaDTO;
import br.com.southsystem.votos.model.Pauta;

public interface PautaService {

	/**
	 * Cadastra uma nova pauta.
	 * @param solicitacaoPautaDTO
	 * @return pauta cadastrada
	 */
	PautaDTO cadastrarPauta(SolicitacaoPautaDTO solicitacaoPautaDTO);
	
	/**
	 * Consulta uma Pauta com o id;
	 * @param id
	 * @return
	 */
	Optional<Pauta> consultarPautaPorId(Long id);
	
}
