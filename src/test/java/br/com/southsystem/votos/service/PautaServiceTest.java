package br.com.southsystem.votos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.southsystem.votos.dao.PautaDAO;
import br.com.southsystem.votos.dto.ContagemVotosDTO;
import br.com.southsystem.votos.dto.SolicitacaoPautaDTO;
import br.com.southsystem.votos.exception.PautaNaoEncontradaException;
import br.com.southsystem.votos.model.Pauta;
import br.com.southsystem.votos.model.StatusContabilizacao;

@SpringBootTest
class PautaServiceTest {

	@InjectMocks
	private PautaServiceImpl pautaService;

	@Mock
	private PautaDAO pautaDAO;

	@Test
	void deve_cadastrar_pauta() {
		var solicitacao = criarSolicitacaoPautaDTO();

		var pautaCadastrada = pautaService.cadastrarPauta(solicitacao);

		assertNotNull(pautaCadastrada);
		assertNotNull(pautaCadastrada.getDhCriacao());
		assertEquals(solicitacao.getAssunto(), pautaCadastrada.getAssunto());
		assertEquals(solicitacao.getCorpo(), pautaCadastrada.getCorpo());
	}

	@Test
	void deve_gerar_resultado_votacao_aprovado() {

		Mockito.when(pautaDAO.findById(1L)).thenReturn(Optional.of(new Pauta()));

		var resultadoVotacao = pautaService.gerarResultadoVotacao(1L, new ContagemVotosDTO(1L, 0L));

		assertNotNull(resultadoVotacao);
		assertEquals(StatusContabilizacao.APROVADO, resultadoVotacao);
	}

	@Test
	void deve_gerar_resultado_votacao_recusado() {
		Mockito.when(pautaDAO.findById(1L)).thenReturn(Optional.of(new Pauta()));

		var resultadoVotacao = pautaService.gerarResultadoVotacao(1L, new ContagemVotosDTO(0L, 1L));

		assertNotNull(resultadoVotacao);
		assertEquals(StatusContabilizacao.RECUSADO, resultadoVotacao);
	}

	@Test
	void deve_gerar_resultado_votacao_inconclusivo() {
		Mockito.when(pautaDAO.findById(1L)).thenReturn(Optional.of(new Pauta()));

		var resultadoVotacao = pautaService.gerarResultadoVotacao(1L, new ContagemVotosDTO(1L, 1L));

		assertNotNull(resultadoVotacao);
		assertEquals(StatusContabilizacao.INCONCLUSIVO, resultadoVotacao);
	}
	
	@Test
	void deve_gerar_exception_pauta_nao_encontrada() {
		
		Assertions.assertThrows(PautaNaoEncontradaException.class, () -> {
			pautaService.gerarResultadoVotacao(1L, null);
		});
	}
	

	private SolicitacaoPautaDTO criarSolicitacaoPautaDTO() {
		var solicitacaoPautaDTO = new SolicitacaoPautaDTO();
		solicitacaoPautaDTO.setAssunto("assunto");
		solicitacaoPautaDTO.setCorpo("corpo");
		return solicitacaoPautaDTO;
	}
}
