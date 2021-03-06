package br.com.southsystem.votos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.southsystem.votos.dao.PautaDAO;
import br.com.southsystem.votos.dto.ContagemVotosDTO;
import br.com.southsystem.votos.dto.PautaDTO;
import br.com.southsystem.votos.dto.SolicitacaoPautaDTO;
import br.com.southsystem.votos.exception.PautaNaoEncontradaException;
import br.com.southsystem.votos.mapper.PautaMapper;
import br.com.southsystem.votos.model.Pauta;
import br.com.southsystem.votos.model.StatusContabilizacao;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PautaServiceImpl implements PautaService {

	@Autowired
	private PautaDAO pautaDAO;
	
	@Autowired
	private VotoService votoService;
	
	public PautaDTO cadastrarPauta(SolicitacaoPautaDTO solicitacaoPautaDTO) {
		var pauta = new Pauta( solicitacaoPautaDTO.getAssunto(), solicitacaoPautaDTO.getCorpo() );
		
		pautaDAO.save( pauta );
		
		return PautaMapper.INSTANCE.toDTO( pauta );
	}

	@Override
	public Optional<Pauta> consultarPautaPorId(Long id) {
		return pautaDAO.findById(id);
	}

	@Override
	public StatusContabilizacao gerarResultadoVotacao(Pauta pauta, ContagemVotosDTO contagemVotos) {
		
		if (contagemVotos.getQtdSim() > contagemVotos.getQtdNao()) {
			pauta.setStatusContabilizacao( StatusContabilizacao.APROVADO );
		} else if (contagemVotos.getQtdSim() < contagemVotos.getQtdNao()) {
			pauta.setStatusContabilizacao( StatusContabilizacao.RECUSADO );
		} else {
			pauta.setStatusContabilizacao( StatusContabilizacao.INCONCLUSIVO );
		}
		
		pautaDAO.save( pauta );
		
		return pauta.getStatusContabilizacao();
	}

	@Override
	public ContagemVotosDTO contabilizarEDarResultado(Long idPauta) {
		var pautaOpt = consultarPautaPorId(idPauta);
		if (pautaOpt.isEmpty()) {
			log.error("Tentando contabilizar a votação em uma Pauta não encontrada.");
			throw new PautaNaoEncontradaException();
		}
		
		var contagem = votoService.contabilizarVotos(idPauta);
		
		if (contagem != null) {
			var resultado = gerarResultadoVotacao(pautaOpt.get(), contagem);
			contagem.setResultado(resultado);
			
			return contagem;			
		}
		
		return new ContagemVotosDTO(0L, 0L, StatusContabilizacao.INCONCLUSIVO);
	}
	
}
