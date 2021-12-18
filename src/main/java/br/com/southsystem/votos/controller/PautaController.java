package br.com.southsystem.votos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.southsystem.votos.dto.PautaDTO;
import br.com.southsystem.votos.dto.SolicitacaoPautaDTO;
import br.com.southsystem.votos.service.PautaService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pauta")
public class PautaController {

	@Autowired
	private PautaService pautaService;
	
	@PostMapping("/v1.0")
	@ApiOperation(value = "Cadastra uma nova pauta")
	public ResponseEntity<PautaDTO> cadastrarPauta(@Valid @RequestBody SolicitacaoPautaDTO solicitacaoPautaDTO) {
		
		try {
			var pauta = pautaService.cadastrarPauta(solicitacaoPautaDTO);
			
			return new ResponseEntity<>(pauta, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
}
