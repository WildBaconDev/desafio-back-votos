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
import org.springframework.boot.test.context.SpringBootTest;

import br.com.southsystem.votos.dao.SessaoDAO;
import br.com.southsystem.votos.dto.SolicitacaoAberturaSessaoDTO;
import br.com.southsystem.votos.exception.PautaComSessaoExistenteException;
import br.com.southsystem.votos.exception.PautaNaoEncontradaException;
import br.com.southsystem.votos.model.Pauta;
import br.com.southsystem.votos.model.Sessao;

@SpringBootTest
class SessaoServiceTest {

	@InjectMocks
	private SessaoServiceImpl sessaoService;
	
	@Mock
	private PautaService pautaService;
	
	@Mock
	private SessaoDAO sessaoDAO;
	
	@Test
	void deve_solicitar_abertura_sessao_com_minutos_determinado() {
		
		Mockito.when(pautaService.consultarPautaPorId(1L)).thenReturn(Optional.of(criarPauta()));
		Mockito.when(sessaoDAO.findByPautaId( 1L )).thenReturn(Optional.ofNullable(null));
		
		var solicitacao = criarSolicitacaoAberturaSessaoDTO();
		
		var sessaoCriada = sessaoService.solicitarAberturaSessao(solicitacao);
		
		assertNotNull(sessaoCriada);
		assertNotNull(sessaoCriada.getDhCriacao());
		assertNotNull(sessaoCriada.getDhFechamento());
		var dhFechamentoEsperado = LocalDateTime.now()
				.plusMinutes(solicitacao.getMinutosSessaoAberta())
				.withNano(0);
		
		var dhFechamentoSalvoSemMilli = sessaoCriada.getDhFechamento().withNano(0);
		
		assertEquals(dhFechamentoEsperado, dhFechamentoSalvoSemMilli);
	}
	
	@Test
	void deve_solicitar_abertura_sessao_com_minuto_padrao() {
		
		Mockito.when(pautaService.consultarPautaPorId(1L)).thenReturn(Optional.of(criarPauta()));
		Mockito.when(sessaoDAO.findByPautaId( 1L )).thenReturn(Optional.ofNullable(null));
		
		var solicitacao = criarSolicitacaoAberturaSessaoDTO();
		solicitacao.setMinutosSessaoAberta(null);
		
		var sessaoCriada = sessaoService.solicitarAberturaSessao(solicitacao);
		
		assertNotNull(sessaoCriada);
		assertNotNull(sessaoCriada.getDhCriacao());
		assertNotNull(sessaoCriada.getDhFechamento());
		var dhFechamentoEsperado = LocalDateTime.now()
				.plusMinutes(1)
				.withNano(0);
		
		var dhFechamentoSalvoSemMilli = sessaoCriada.getDhFechamento().withNano(0);
		
		assertEquals(dhFechamentoEsperado, dhFechamentoSalvoSemMilli);
	}
	
	@Test
	void deve_gerar_exception_pauta_nao_encontrada() {
		var solicitacao = criarSolicitacaoAberturaSessaoDTO();
		
		Assertions.assertThrows(PautaNaoEncontradaException.class, () -> {
			sessaoService.solicitarAberturaSessao(solicitacao);			
		});
	}
	
	@Test
	void deve_gerar_exception_pauta_com_sessao_existente() {
		var solicitacao = criarSolicitacaoAberturaSessaoDTO();
		
		Mockito.when(pautaService.consultarPautaPorId(1L)).thenReturn(Optional.of(criarPauta()));
		Mockito.when(sessaoDAO.findByPautaId( 1L )).thenReturn(Optional.ofNullable(new Sessao()));
		
		Assertions.assertThrows(PautaComSessaoExistenteException.class, () -> {
			sessaoService.solicitarAberturaSessao(solicitacao);			
		});
	}

	private Pauta criarPauta() {
		var pauta = new Pauta();
		pauta.setId(1L);
		return pauta;
	}
	
	private SolicitacaoAberturaSessaoDTO criarSolicitacaoAberturaSessaoDTO() {
		var solicitacaoAbertura = new SolicitacaoAberturaSessaoDTO();
		solicitacaoAbertura.setIdPauta(1L);
		solicitacaoAbertura.setMinutosSessaoAberta(120);
		return solicitacaoAbertura;
	}
	
}
