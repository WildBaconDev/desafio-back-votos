package br.com.southsystem.votos.service;

import br.com.southsystem.votos.dto.ContagemVotosDTO;
import br.com.southsystem.votos.dto.SolicitacaoVotoDTO;
import br.com.southsystem.votos.dto.VotoDTO;

public interface VotoService {

	/**
	 * Recebe voto do associado em uma pauta. O associado só pode votar uma vez por pauta.
	 * @param solicitacaoVotoDTO
	 * @return
	 */
	VotoDTO votar(SolicitacaoVotoDTO solicitacaoVotoDTO);
	
	/**
	 * Contabiliza os votos
	 * @param idPauta
	 * @return
	 */
	ContagemVotosDTO contabilizarVotos(Long idPauta);
	
}
