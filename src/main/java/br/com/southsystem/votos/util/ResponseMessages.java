package br.com.southsystem.votos.util;

public final class ResponseMessages {

	private ResponseMessages() {
		throw new IllegalStateException("Utility class");
	}

	public static final String VOTO_CADASTRADO_COM_SUCESSO = "Voto cadastrado com sucesso!";
	public static final String CONTAGEM_EFETUADA_COM_SUCESSO = "Contabilização e a geração de resultado foram efetuadas com sucesso";
	public static final String PAUTA_NAO_ENCONTRADA = "Pauta não encontrada!";
	public static final String SESSAO_ABERTA_COM_SUCESSO = "Sessão aberta com sucesso!";
	public static final String PAUTA_CADASTRADA_COM_SUCESSO = "Pauta cadastrada com sucesso!";
	public static final String INTERNAL_SERVER = "Internal server error!";

}
