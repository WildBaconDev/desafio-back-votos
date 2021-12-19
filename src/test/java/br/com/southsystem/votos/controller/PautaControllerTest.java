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

import br.com.southsystem.votos.dto.SolicitacaoPautaDTO;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class PautaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void deve_cadastrar_pauta() throws Exception {
		String json = new Gson().toJson( criarSolicitacaoPautaDTO() );
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.post("/pauta/v1.0")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON)
				).andExpect(MockMvcResultMatchers.status().is( HttpStatus.CREATED.value() ));	
	}
	
	@Test
	void deve_contabilizar() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders
					.put("/pauta/v1.0/contabilizar/1")
					.contentType(MediaType.APPLICATION_JSON)
				).andExpect(MockMvcResultMatchers.status().is( HttpStatus.OK.value() ));	
	}
	
	private SolicitacaoPautaDTO criarSolicitacaoPautaDTO() {
		var solicitacaoPautaDTO = new SolicitacaoPautaDTO();
		solicitacaoPautaDTO.setAssunto("assunto");
		solicitacaoPautaDTO.setCorpo("corpo");
		return solicitacaoPautaDTO;
	}
	
}
