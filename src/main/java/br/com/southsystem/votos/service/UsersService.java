package br.com.southsystem.votos.service;

import br.com.southsystem.votos.model.AptoVotar;

public interface UsersService {

	/**
	 * Integração com o sistema de verificação do usuário pelo CPF.
	 * Caso o CPF seja inválido, a API retornará o HTTP Status 404 (Not found). Você pode usar geradores de CPF para gerar CPFs válidos; 
	 * Caso o CPF seja válido, a API retornará se o usuário pode (ABLE_TO_VOTE) ou não pode (UNABLE_TO_VOTE) executar a operação Exemplos de retorno do serviço
	 * @param cpf
	 * @return
	 */
	AptoVotar consultaCpf(String cpf);
	
	/**
	 * Integração com o gerador.app para gerar o CPF
	 * @return
	 */
	String gerarCpf();
	
}
