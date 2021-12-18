package br.com.southsystem.votos.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;

import br.com.southsystem.votos.dto.SolicitacaoAberturaSessaoDTO;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class SessaoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void deve_abrir_sessao() throws Exception {
		String json = new Gson().toJson( criarSolicitacaoAberturaSessaoDTO() );
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.post("/sessao/v1.0/abrir")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON)
				).andExpect(MockMvcResultMatchers.status().is( HttpStatus.CREATED.value() ));	
	}
	
	private SolicitacaoAberturaSessaoDTO criarSolicitacaoAberturaSessaoDTO() {
		var solicitacaoAbertura = new SolicitacaoAberturaSessaoDTO();
		solicitacaoAbertura.setIdPauta(2L);
		solicitacaoAbertura.setMinutosSessaoAberta(120);
		return solicitacaoAbertura;
	}
}
