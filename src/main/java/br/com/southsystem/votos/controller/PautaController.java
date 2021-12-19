package br.com.southsystem.votos.controller;

import static br.com.southsystem.votos.util.ResponseMessages.CONTAGEM_EFETUADA_COM_SUCESSO;
import static br.com.southsystem.votos.util.ResponseMessages.INTERNAL_SERVER;
import static br.com.southsystem.votos.util.ResponseMessages.PAUTA_CADASTRADA_COM_SUCESSO;
import static br.com.southsystem.votos.util.ResponseMessages.PAUTA_NAO_ENCONTRADA;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.southsystem.votos.dto.ContagemVotosDTO;
import br.com.southsystem.votos.dto.PautaDTO;
import br.com.southsystem.votos.dto.SolicitacaoPautaDTO;
import br.com.southsystem.votos.exception.PautaNaoEncontradaException;
import br.com.southsystem.votos.exception.PautaSemSessaoException;
import br.com.southsystem.votos.service.PautaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/pauta")
@Slf4j
public class PautaController {

	@Autowired
	private PautaService pautaService;
	
	@PostMapping("/v1.0")
	@ApiOperation(value = "Cadastra uma nova pauta")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = PAUTA_CADASTRADA_COM_SUCESSO, response = PautaDTO.class),
			@ApiResponse(code = 500, message = INTERNAL_SERVER) 
	})
	public ResponseEntity<PautaDTO> cadastrarPauta(@Valid @RequestBody SolicitacaoPautaDTO solicitacaoPautaDTO) {
		
		try {
			var pauta = pautaService.cadastrarPauta(solicitacaoPautaDTO);
			
			return new ResponseEntity<>(pauta, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Internal server error!" + e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	@PutMapping("/v1.0/contabilizar/{idPauta}")
	@ApiOperation(value = "Contabiliza os votos e da o resultado da votação na pauta.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = CONTAGEM_EFETUADA_COM_SUCESSO, response = ContagemVotosDTO.class),
			@ApiResponse(code = 404, message = PAUTA_NAO_ENCONTRADA),
			@ApiResponse(code = 500, message = INTERNAL_SERVER) 
	})
	public ResponseEntity<ContagemVotosDTO> contabilizar(@NotNull @PathVariable("idPauta") Long idPauta) {

		try {
			return new ResponseEntity<>(pautaService.contabilizarEDarResultado(idPauta), HttpStatus.OK);
		} catch (PautaNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (PautaSemSessaoException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			log.error("Internal server error!" + e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
	
}
