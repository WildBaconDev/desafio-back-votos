package br.com.southsystem.votos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.southsystem.votos.dao.VotoDAO;
import br.com.southsystem.votos.dto.ContagemVotosDTO;
import br.com.southsystem.votos.dto.SolicitacaoVotoDTO;
import br.com.southsystem.votos.exception.AssociadoImpossibilitadoVotarException;
import br.com.southsystem.votos.exception.AssociadoVotandoNovamenteException;
import br.com.southsystem.votos.exception.PautaNaoEncontradaException;
import br.com.southsystem.votos.exception.PautaSemSessaoException;
import br.com.southsystem.votos.exception.SessaoVotacaoFechadaException;
import br.com.southsystem.votos.model.AptoVotar;
import br.com.southsystem.votos.model.Pauta;
import br.com.southsystem.votos.model.Sessao;
import br.com.southsystem.votos.model.StatusContabilizacao;
import br.com.southsystem.votos.model.Voto;

@SpringBootTest
class VotoServiceTest {

	@InjectMocks
	private VotoServiceImpl votoService;
	
	@Mock
	private VotoDAO votoDAO;
	
	@Mock
	private PautaService pautaService;
	
	@Mock
	private UsersService usersService;

	@Test
	void deve_votar_com_sucesso() {
		var solicitacaoVoto = criarSolicitacaoVotoDTO(true);
	
		Mockito.when( pautaService.consultarPautaPorId(solicitacaoVoto.getIdPauta()) ).thenReturn(criarPauta());
		var votoCadastrado = votoService.votar(solicitacaoVoto);
		
		assertNotNull(votoCadastrado);
		assertNotNull(votoCadastrado.getDhCriacao());
		assertTrue(votoCadastrado.isValorVoto());
	}
	
	@Test
	void deve_gerar_exception_pauta_nao_encontrada() {
		var solicitacao = criarSolicitacaoVotoDTO(true);
		
		Assertions.assertThrows(PautaNaoEncontradaException.class, () -> {
			votoService.votar(solicitacao);			
		});
	}
	
	@Test
	void deve_gerar_exception_pauta_sem_sessao() {
		var solicitacao = criarSolicitacaoVotoDTO(true);
		var pauta = criarPauta();
		pauta.get().setSessao(null);
		Mockito.when( pautaService.consultarPautaPorId(solicitacao.getIdPauta()) ).thenReturn(pauta);
		Assertions.assertThrows(PautaSemSessaoException.class, () -> {
			votoService.votar(solicitacao);			
		});
	}
	
	@Test
	void deve_gerar_exception_associado_votando_novamente() {
		var solicitacao = criarSolicitacaoVotoDTO(true);
		var pauta = criarPauta();
		Mockito.when( pautaService.consultarPautaPorId(solicitacao.getIdPauta()) ).thenReturn(pauta);
		Mockito.when( votoDAO.findByIdAssociadoAndSessaoId( solicitacao.getIdAssociado(), pauta.get().getSessao().getId() ) )
			.thenReturn(Optional.ofNullable(new Voto()));
		Assertions.assertThrows(AssociadoVotandoNovamenteException.class, () -> {
			votoService.votar(solicitacao);			
		});
	}
	
	@Test
	void deve_gerar_exception_sessao_fechada() {
		var solicitacao = criarSolicitacaoVotoDTO(true);
		var pauta = criarPauta();
		pauta.get().getSessao().setDhFechamento(LocalDateTime.now().minusSeconds(1).withNano(0));
		Mockito.when( pautaService.consultarPautaPorId(solicitacao.getIdPauta()) ).thenReturn(pauta);
		Assertions.assertThrows(SessaoVotacaoFechadaException.class, () -> {
			votoService.votar(solicitacao);			
		});
	}
	
	@Test
	void deve_gerar_exception_associado_inapto_votar() {
		var solicitacao = criarSolicitacaoVotoDTO(true);
		
		Mockito.when( pautaService.consultarPautaPorId(solicitacao.getIdPauta()) ).thenReturn(criarPauta());
		Mockito.when( usersService.consultaCpf(null) ).thenReturn(AptoVotar.UNABLE_TO_VOTE);
		Assertions.assertThrows(AssociadoImpossibilitadoVotarException.class, () -> {
			votoService.votar(solicitacao);			
		});
	}
	
	@Test
	void deve_contabilizar_e_dar_resultado() {
		
		Mockito.when(pautaService.consultarPautaPorId( 1L )).thenReturn( criarPauta() );
		var contagem = new ContagemVotosDTO(2L, 0L);
		Mockito.when( votoDAO.contabilizarVotos( 1L )).thenReturn( contagem );
		Mockito.when( pautaService.gerarResultadoVotacao(1L, contagem) )
			.thenReturn(StatusContabilizacao.APROVADO);
		
		var resultado = votoService.contabilizarEDarResultado(1L);
		
		assertNotNull(resultado);
		assertEquals(2L, resultado.getQtdSim());
		assertEquals(0L, resultado.getQtdNao());
		assertEquals(StatusContabilizacao.APROVADO, resultado.getResultado());
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
	
	private SolicitacaoVotoDTO criarSolicitacaoVotoDTO(Boolean voto) {
		var solicitacao = new SolicitacaoVotoDTO();
		solicitacao.setIdAssociado(1L);
		solicitacao.setIdPauta(1L);
		solicitacao.setVoto(voto);
		return solicitacao;
	}
	
}
