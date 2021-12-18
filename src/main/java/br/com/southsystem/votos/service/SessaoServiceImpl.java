package br.com.southsystem.votos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.southsystem.votos.dao.SessaoDAO;
import br.com.southsystem.votos.dto.SessaoDTO;
import br.com.southsystem.votos.dto.SolicitacaoAberturaSessaoDTO;
import br.com.southsystem.votos.exception.PautaComSessaoExistenteException;
import br.com.southsystem.votos.exception.PautaNaoEncontradaException;
import br.com.southsystem.votos.mapper.SessaoMapper;
import br.com.southsystem.votos.model.Sessao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SessaoServiceImpl implements SessaoService {
	
	@Autowired
	private PautaService pautaService;
	
	@Autowired
	private SessaoDAO sessaoDAO;
	
	@Override
	public SessaoDTO solicitarAberturaSessao(SolicitacaoAberturaSessaoDTO solicitacaoAberturaSessaoDTO) {
		var pauta = pautaService.consultarPautaPorId(solicitacaoAberturaSessaoDTO.getIdPauta());
		
		if (pauta.isEmpty()	) {
			log.error("Tentando solicitar uma abertura de sessão com uma Pauta não encontrada.");
			throw new PautaNaoEncontradaException();
		}
		
		var sessaoAbertaParaPauta = sessaoDAO.findByPautaId( pauta.get().getId() );
		if (sessaoAbertaParaPauta.isPresent()) {
			log.error("Tentando solicitar uma abertura de sessão com uma Pauta que já possui sessão.");
			throw new PautaComSessaoExistenteException();
		}
		
		var sessao = new Sessao(pauta.get(), solicitacaoAberturaSessaoDTO.getMinutosSessaoAberta());
		
		return SessaoMapper.INSTANCE.toDTO( sessaoDAO.save( sessao ) );
	}

}
