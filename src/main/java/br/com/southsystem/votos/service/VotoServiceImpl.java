package br.com.southsystem.votos.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.southsystem.votos.dao.VotoDAO;
import br.com.southsystem.votos.dto.ContagemVotosDTO;
import br.com.southsystem.votos.dto.SolicitacaoVotoDTO;
import br.com.southsystem.votos.dto.VotoDTO;
import br.com.southsystem.votos.exception.AssociadoVotandoNovamenteException;
import br.com.southsystem.votos.exception.PautaNaoEncontradaException;
import br.com.southsystem.votos.exception.PautaSemSessaoException;
import br.com.southsystem.votos.exception.SessaoVotacaoFechadaException;
import br.com.southsystem.votos.mapper.VotoMapper;
import br.com.southsystem.votos.model.Pauta;
import br.com.southsystem.votos.model.Voto;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VotoServiceImpl implements VotoService {

	@Autowired
	private VotoDAO votoDAO;
	
	@Autowired
	private PautaService pautaService;
	
	@Override
	public VotoDTO votar(SolicitacaoVotoDTO solicitacaoVotoDTO) {
		var pautaOpt = pautaService.consultarPautaPorId( solicitacaoVotoDTO.getIdPauta() );
		
		validarPauta(pautaOpt);
		
		var pauta = pautaOpt.get();
		
		var votoDesteAssociado = votoDAO.findByIdAssociadoAndSessaoId( solicitacaoVotoDTO.getIdAssociado(), pauta.getSessao().getId() );
		if (votoDesteAssociado.isPresent()) {
			log.error("Associado tentando votar novamente na pauta.");
			throw new AssociadoVotandoNovamenteException();
		}
		
		
		if ( LocalDateTime.now().isAfter(pautaOpt.get().getSessao().getDhFechamento()) ) {
			log.error("Tentando votar em uma sessão já fechada.");
			throw new SessaoVotacaoFechadaException();
		}
		
		var voto = new Voto( solicitacaoVotoDTO.isVoto(), pautaOpt.get().getSessao(), solicitacaoVotoDTO.getIdAssociado() );
		
		votoDAO.save( voto );
		
		return VotoMapper.INSTANCE.toDTO( voto );
	}

	private void validarPauta(Optional<Pauta> pauta) {
		if (pauta.isEmpty()) {
			log.error("Tentando votar em uma Pauta não encontrada.");
			throw new PautaNaoEncontradaException();
		}
		
		if (pauta.get().getSessao() == null) {
			log.error("Tentando votar em uma pauta sem sessão aberta.");
			throw new PautaSemSessaoException();
		}
	}

	@Override
	public ContagemVotosDTO contabilizarEDarResultado(Long idPauta) {
		var pauta = pautaService.consultarPautaPorId( idPauta );
		
		validarPauta(pauta);
		
		var contagem = votoDAO.contabilizarVotos( pauta.get().getSessao().getId() );
		var resultado = pautaService.gerarResultadoVotacao(idPauta, contagem);
		contagem.setResultado( resultado );
		
		return contagem;
	}

}
