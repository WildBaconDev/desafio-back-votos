package br.com.southsystem.votos.service;

import java.time.LocalDateTime;

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
		var pauta = pautaService.consultarPautaPorId( solicitacaoVotoDTO.getIdPauta() );
		
		if (pauta.isEmpty()) {
			log.error("Tentando votar em uma Pauta não encontrada.");
			throw new PautaNaoEncontradaException();
		}
		
		var votoDesteAssociado = votoDAO.findByIdAssociado( solicitacaoVotoDTO.getIdAssociado() );
		if (votoDesteAssociado.isPresent()) {
			log.error("Associado tentando votar novamente na pauta.");
			throw new AssociadoVotandoNovamenteException();
		}
		
		
		if (pauta.get().getSessao() == null) {
			log.error("Tentando votar em uma pauta sem sessão aberta.");
			throw new PautaSemSessaoException();
		}
		
		if ( LocalDateTime.now().isAfter(pauta.get().getSessao().getDhFechamento()) ) {
			log.error("Tentando votar em uma sessão já fechada.");
			throw new SessaoVotacaoFechadaException();
		}
		
		var voto = new Voto( solicitacaoVotoDTO.isVoto(), pauta.get().getSessao(), solicitacaoVotoDTO.getIdAssociado() );
		
		return VotoMapper.INSTANCE.toDTO( votoDAO.save( voto ) );
	}

	@Override
	public ContagemVotosDTO contabilizarVotos(Long idPauta) {
		var pauta = pautaService.consultarPautaPorId( idPauta );
		
		if (pauta.isEmpty()) {
			log.error("Tentando contabilizar a votação em uma Pauta não encontrada.");
			throw new PautaNaoEncontradaException();
		}
		
		if (pauta.get().getSessao() == null) {
			log.error("Tentando contabilizar a votação em uma Pauta sem sessão.");
			throw new PautaSemSessaoException();
		}
		
		var contagem = votoDAO.contabilizarVotos( pauta.get().getSessao().getId() );
		var resultado = pautaService.contabilizarVotos(idPauta, contagem);
		contagem.setResultado( resultado );
		
		return contagem;
	}

}
