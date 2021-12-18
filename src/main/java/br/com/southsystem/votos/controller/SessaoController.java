package br.com.southsystem.votos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.southsystem.votos.dto.SessaoDTO;
import br.com.southsystem.votos.dto.SolicitacaoAberturaSessaoDTO;
import br.com.southsystem.votos.exception.PautaNaoEncontradaException;
import br.com.southsystem.votos.service.SessaoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/sessao")
public class SessaoController {
	
	@Autowired
	private SessaoService sessaoService;
	
	@PostMapping("/v1.0/abrir")
	@ApiOperation(value = "Abre uma sessão de votação em uma pauta")
	public ResponseEntity<SessaoDTO> abrirSessao(SolicitacaoAberturaSessaoDTO solicitacaoAberturaSessaoDTO) {
		try {
			var sessao = sessaoService.solicitarAberturaSessao(solicitacaoAberturaSessaoDTO);
			
			return new ResponseEntity<>(sessao, HttpStatus.CREATED);
		} catch (PautaNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

}
