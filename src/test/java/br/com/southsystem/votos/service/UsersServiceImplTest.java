package br.com.southsystem.votos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.southsystem.votos.dto.StatusAssociadoDTO;
import br.com.southsystem.votos.exception.CpfInvalidoException;
import br.com.southsystem.votos.exception.HttpStatusNaoEsperadoException;
import br.com.southsystem.votos.model.AptoVotar;

@SpringBootTest
class UsersServiceImplTest {

	private static final String CPF = "707.255.060-75";

	@InjectMocks
	private UsersServiceImpl userService;
	
	@Mock
	private RestTemplate restTemplate;
	
	@Test
	void deve_retornar_cpf() {
		final String cpf = CPF;
		Mockito.when(  restTemplate.getForEntity(null + "/" + cpf, StatusAssociadoDTO.class) )
			.thenReturn(new ResponseEntity<>(new StatusAssociadoDTO(AptoVotar.ABLE_TO_VOTE), HttpStatus.OK));
		
		var apto = userService.consultaCpf(cpf);
		
		assertNotNull(apto);
		assertEquals(AptoVotar.ABLE_TO_VOTE, apto);
	}
	
	@Test
	void deve_gerar_cpf_invalido_exception() {
		final String cpf = CPF;
		Mockito.when(  restTemplate.getForEntity(null + "/" + cpf, StatusAssociadoDTO.class) )
			.thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
		
		
		Assertions.assertThrows(CpfInvalidoException.class, () -> {
			userService.consultaCpf(cpf);
		});
	}
	
	@Test
	void deve_gerar_status_nao_esperado_exception() {
		final String cpf = CPF;
		Mockito.when(  restTemplate.getForEntity(null + "/" + cpf, StatusAssociadoDTO.class) )
			.thenReturn(new ResponseEntity<>(null, HttpStatus.I_AM_A_TEAPOT));
		
		
		Assertions.assertThrows(HttpStatusNaoEsperadoException.class, () -> {
			userService.consultaCpf(cpf);
		});
	}

}
