package br.com.southsystem.votos.service;

import br.com.southsystem.votos.dto.PautaDTO;
import br.com.southsystem.votos.dto.SolicitacaoPautaDTO;

public interface PautaService {

	PautaDTO cadastrarPauta(SolicitacaoPautaDTO solicitacaoPautaDTO);
	
}
