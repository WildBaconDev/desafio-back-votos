package br.com.southsystem.votos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.southsystem.votos.dao.PautaDAO;
import br.com.southsystem.votos.dto.ContagemVotosDTO;
import br.com.southsystem.votos.dto.SolicitacaoPautaDTO;
import br.com.southsystem.votos.exception.PautaNaoEncontradaException;
import br.com.southsystem.votos.model.Pauta;
import br.com.southsystem.votos.model.Sessao;
import br.com.southsystem.votos.model.StatusContabilizacao;

@SpringBootTest
@AutoConfigureTestDatabase
class PautaServiceTest {

	@InjectMocks
	private PautaServiceImpl pautaService;

	@Mock
	private PautaDAO pautaDAO;

	@Mock
	private VotoServiceImpl votoService;
	
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

		var resultadoVotacao = pautaService.gerarResultadoVotacao(new Pauta(), new ContagemVotosDTO(1L, 0L));

		assertNotNull(resultadoVotacao);
		assertEquals(StatusContabilizacao.APROVADO, resultadoVotacao);
	}

	@Test
	void deve_gerar_resultado_votacao_recusado() {
		Mockito.when(pautaDAO.findById(1L)).thenReturn(Optional.of(new Pauta()));

		var resultadoVotacao = pautaService.gerarResultadoVotacao(new Pauta(), new ContagemVotosDTO(0L, 1L));

		assertNotNull(resultadoVotacao);
		assertEquals(StatusContabilizacao.RECUSADO, resultadoVotacao);
	}

	@Test
	void deve_gerar_resultado_votacao_inconclusivo() {
		Mockito.when(pautaDAO.findById(1L)).thenReturn(Optional.of(new Pauta()));

		var resultadoVotacao = pautaService.gerarResultadoVotacao(new Pauta(), new ContagemVotosDTO(1L, 1L));

		assertNotNull(resultadoVotacao);
		assertEquals(StatusContabilizacao.INCONCLUSIVO, resultadoVotacao);
	}
	
	@Test
	void deve_contabilizar_e_dar_resultado() {
		
		Mockito.when(pautaService.consultarPautaPorId( 1L )).thenReturn( criarPauta() );
		var contagem = new ContagemVotosDTO(2L, 0L);
		Mockito.when( votoService.contabilizarVotos( 1L )).thenReturn( contagem );
		
		var resultado = pautaService.contabilizarEDarResultado(1L);
		
		assertNotNull(resultado);
		assertEquals(2L, resultado.getQtdSim());
		assertEquals(0L, resultado.getQtdNao());
		assertEquals(StatusContabilizacao.APROVADO, resultado.getResultado());
	}
	
	@Test
	void deve_contabilizar_porem_sem_votos() {
		
		Mockito.when(pautaService.consultarPautaPorId( 1L )).thenReturn( criarPauta() );
		Mockito.when( votoService.contabilizarVotos( 1L )).thenReturn( null );
		
		var resultado = pautaService.contabilizarEDarResultado(1L);
		
		assertNotNull(resultado);
		assertEquals(0L, resultado.getQtdSim());
		assertEquals(0L, resultado.getQtdNao());
		assertEquals(StatusContabilizacao.INCONCLUSIVO, resultado.getResultado());
	}
	
	@Test
	void deve_gerar_exception_pauta_nao_encontrada() {
		
		Assertions.assertThrows(PautaNaoEncontradaException.class, () -> {
			pautaService.contabilizarEDarResultado(1L);
		});
	}
	
	private Optional<Pauta> criarPauta() {
		var pauta = new Pauta();
		pauta.setId(1L);
		var sessao = new Sessao();
		sessao.setId(1L);
		sessao.setDhFechamento( LocalDateTime.now().plusMinutes(120).withNano(0) );
		pauta.setSessao(sessao);
		return Optional.of(pauta);
	}

	private SolicitacaoPautaDTO criarSolicitacaoPautaDTO() {
		var solicitacaoPautaDTO = new SolicitacaoPautaDTO();
		solicitacaoPautaDTO.setAssunto("assunto");
		solicitacaoPautaDTO.setCorpo("corpo");
		return solicitacaoPautaDTO;
	}
}
