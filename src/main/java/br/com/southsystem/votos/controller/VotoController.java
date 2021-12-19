package br.com.southsystem.votos.controller;

import static br.com.southsystem.votos.util.ResponseMessages.INTERNAL_SERVER;
import static br.com.southsystem.votos.util.ResponseMessages.PAUTA_NAO_ENCONTRADA;
import static br.com.southsystem.votos.util.ResponseMessages.VOTO_CADASTRADO_COM_SUCESSO;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.southsystem.votos.dto.SolicitacaoVotoDTO;
import br.com.southsystem.votos.dto.VotoDTO;
import br.com.southsystem.votos.exception.AssociadoImpossibilitadoVotarException;
import br.com.southsystem.votos.exception.AssociadoVotandoNovamenteException;
import br.com.southsystem.votos.exception.PautaNaoEncontradaException;
import br.com.southsystem.votos.exception.PautaSemSessaoException;
import br.com.southsystem.votos.exception.SessaoVotacaoFechadaException;
import br.com.southsystem.votos.service.VotoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/voto")
@Slf4j
public class VotoController {

	@Autowired
	private VotoService votoService;

	@PostMapping("/v1.0")
	@ApiOperation(value = "Recebe voto do associado em pauta")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = VOTO_CADASTRADO_COM_SUCESSO, response = VotoDTO.class),
			@ApiResponse(code = 404, message = PAUTA_NAO_ENCONTRADA),
			@ApiResponse(code = 500, message = INTERNAL_SERVER) 
	})
	public ResponseEntity<VotoDTO> votar(@Valid @RequestBody SolicitacaoVotoDTO solicitacaoVotoDTO) {
		try {
			return new ResponseEntity<>(votoService.votar(solicitacaoVotoDTO), HttpStatus.CREATED);
		} catch (PautaNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (AssociadoVotandoNovamenteException | SessaoVotacaoFechadaException | PautaSemSessaoException | AssociadoImpossibilitadoVotarException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			log.error("Internal server error!" + e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
}
