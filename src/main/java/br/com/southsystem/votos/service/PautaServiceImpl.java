package br.com.southsystem.votos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.southsystem.votos.dao.PautaDAO;
import br.com.southsystem.votos.dto.PautaDTO;
import br.com.southsystem.votos.dto.SolicitacaoPautaDTO;
import br.com.southsystem.votos.mapper.PautaMapper;
import br.com.southsystem.votos.model.Pauta;

@Service
public class PautaServiceImpl implements PautaService {

	@Autowired
	private PautaDAO pautaDAO;
	
	public PautaDTO cadastrarPauta(SolicitacaoPautaDTO solicitacaoPautaDTO) {
		var pauta = new Pauta( solicitacaoPautaDTO.getAssunto(), solicitacaoPautaDTO.getCorpo() );
		
		pautaDAO.save( pauta );
		
		return PautaMapper.INSTANCE.toDTO( pauta );
	}
	
}
