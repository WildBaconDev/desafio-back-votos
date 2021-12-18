package br.com.southsystem.votos.controller;

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
import br.com.southsystem.votos.exception.AssociadoVotandoNovamenteException;
import br.com.southsystem.votos.exception.PautaNaoEncontradaException;
import br.com.southsystem.votos.exception.PautaSemSessaoException;
import br.com.southsystem.votos.service.VotoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/voto")
public class VotoController {

	@Autowired
	private VotoService votoService;
	
	@PostMapping("/v1.0/votar")
	@ApiOperation(value = "Recebe voto do associado em pauta")
	public ResponseEntity<VotoDTO> votar(@Valid @RequestBody SolicitacaoVotoDTO solicitacaoVotoDTO) {
		try {
			return new ResponseEntity<>(votoService.votar(solicitacaoVotoDTO), HttpStatus.CREATED);
		} catch (PautaNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (AssociadoVotandoNovamenteException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PutMapping("/v1.0/contabilizar/{idPauta}")
	@ApiOperation(value = "Contabiliza os votos e da o resultado da votação na pauta.")
	public ResponseEntity<ContagemVotosDTO> contabilizar(@NotNull @PathVariable("idPauta") Long idPauta) {
		
		try {
			return new ResponseEntity<>(votoService.contabilizarVotos(idPauta), HttpStatus.OK);
		} catch (PautaNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (PautaSemSessaoException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
}
