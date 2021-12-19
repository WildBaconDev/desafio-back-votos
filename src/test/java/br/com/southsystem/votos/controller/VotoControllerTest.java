package br.com.southsystem.votos.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;

import br.com.southsystem.votos.dto.SolicitacaoVotoDTO;
import br.com.southsystem.votos.dto.VotoDTO;
import br.com.southsystem.votos.service.VotoService;

@ActiveProfiles("test")
@WebMvcTest(VotoController.class)
class VotoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VotoService votoService;
	
	@Test
	void deve_votar() throws Exception {
		String json = new Gson().toJson( criarSolicitacaoVotoDTO(true) );
				
		Mockito.when(votoService.votar(criarSolicitacaoVotoDTO(true))).thenReturn(new VotoDTO());
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.post("/voto/v1.0")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON)
				).andExpect(MockMvcResultMatchers.status().is( HttpStatus.CREATED.value() ));	
	}
	
	private SolicitacaoVotoDTO criarSolicitacaoVotoDTO(Boolean voto) {
		var solicitacao = new SolicitacaoVotoDTO();
		solicitacao.setIdAssociado(2L);
		solicitacao.setIdPauta(1L);
		solicitacao.setVoto(voto);
		return solicitacao;
	}
}
