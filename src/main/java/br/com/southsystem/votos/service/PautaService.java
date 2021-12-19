package br.com.southsystem.votos.service;

import java.util.Optional;

import br.com.southsystem.votos.dto.ContagemVotosDTO;
import br.com.southsystem.votos.dto.PautaDTO;
import br.com.southsystem.votos.dto.SolicitacaoPautaDTO;
import br.com.southsystem.votos.model.Pauta;
import br.com.southsystem.votos.model.StatusContabilizacao;

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
	
	/**
	 * Contabiliza os votos e da o resultado da votação na pauta.
	 * @param idPauta
	 * @return
	 */
	ContagemVotosDTO contabilizarEDarResultado(Long idPauta); 
	/**
	 * Salva no banco o resultado da votação baseado na contagem.
	 * @param pauta
	 * @param contagemVotos
	 * @return
	 */
	StatusContabilizacao gerarResultadoVotacao(Pauta pauta, ContagemVotosDTO contagemVotos);
	
	
}
