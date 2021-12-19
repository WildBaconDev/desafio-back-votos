package br.com.southsystem.votos.controller;

import static br.com.southsystem.votos.util.ResponseMessages.INTERNAL_SERVER;
import static br.com.southsystem.votos.util.ResponseMessages.PAUTA_NAO_ENCONTRADA;
import static br.com.southsystem.votos.util.ResponseMessages.SESSAO_ABERTA_COM_SUCESSO;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.southsystem.votos.dto.SessaoDTO;
import br.com.southsystem.votos.dto.SolicitacaoAberturaSessaoDTO;
import br.com.southsystem.votos.exception.PautaComSessaoExistenteException;
import br.com.southsystem.votos.exception.PautaNaoEncontradaException;
import br.com.southsystem.votos.service.SessaoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sessao")
@Slf4j
public class SessaoController {
	
	@Autowired
	private SessaoService sessaoService;
	
	@PostMapping("/v1.0/abrir")
	@ApiOperation(value = "Abre uma sessão de votação em uma pauta")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = SESSAO_ABERTA_COM_SUCESSO, response = SessaoDTO.class),
			@ApiResponse(code = 404, message = PAUTA_NAO_ENCONTRADA),
			@ApiResponse(code = 500, message = INTERNAL_SERVER) 
	})
	public ResponseEntity<SessaoDTO> abrirSessao(@Valid @RequestBody SolicitacaoAberturaSessaoDTO solicitacaoAberturaSessaoDTO) {
		try {
			var sessao = sessaoService.solicitarAberturaSessao(solicitacaoAberturaSessaoDTO);
			
			return new ResponseEntity<>(sessao, HttpStatus.CREATED);
		} catch (PautaNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (PautaComSessaoExistenteException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			log.error("Internal server error!" + e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

}
