package br.com.southsystem.votos.controller;

import static br.com.southsystem.votos.util.ResponseMessages.*;

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

@RestController
@RequestMapping("/voto")
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
			return ResponseEntity.internalServerError().build();
		}
	}

	@PutMapping("/v1.0/contabilizar/{idPauta}")
	@ApiOperation(value = "Contabiliza os votos e da o resultado da votação na pauta.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = CONTAGEM_EFETUADA_COM_SUCESSO, response = VotoDTO.class),
			@ApiResponse(code = 404, message = PAUTA_NAO_ENCONTRADA),
			@ApiResponse(code = 500, message = INTERNAL_SERVER) 
	})
	public ResponseEntity<ContagemVotosDTO> contabilizar(@NotNull @PathVariable("idPauta") Long idPauta) {

		try {
			return new ResponseEntity<>(votoService.contabilizarEDarResultado(idPauta), HttpStatus.OK);
		} catch (PautaNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (PautaSemSessaoException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
}
