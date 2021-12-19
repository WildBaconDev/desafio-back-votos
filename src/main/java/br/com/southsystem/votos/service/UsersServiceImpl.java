package br.com.southsystem.votos.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.southsystem.votos.dto.GeradorCPFDTO;
import br.com.southsystem.votos.dto.StatusAssociadoDTO;
import br.com.southsystem.votos.exception.CpfInvalidoException;
import br.com.southsystem.votos.exception.HttpStatusNaoEsperadoException;
import br.com.southsystem.votos.model.AptoVotar;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {
	
	@Value("${user.service.url}")
	private String userUrl;
	
	@Value("${gerador.app.cpf.url}")
	private String geradorAppUrl;
	
	@Value("${gerador.app.token}")
	private String token;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public AptoVotar consultaCpf(String cpf) {
		if (cpf == null) return null;
		
		try {
			log.info("Requisitando a validação do CPF " + cpf);
			var response = restTemplate.getForEntity(userUrl + "/" + cpf, StatusAssociadoDTO.class);
			
			if ( HttpStatus.NOT_FOUND.equals(response.getStatusCode()) ) {
				log.error("CPF Inválido");
				throw new CpfInvalidoException();
			} else if (!HttpStatus.OK.equals( response.getStatusCode() )) {
				log.error("Erro não esperado");
				throw new HttpStatusNaoEsperadoException("Status code não esperado");
			}
			var status = response.getBody().getStatus();
			log.info("Requisição finalizada com sucesso. STATUS: " + status);
			return status;
		} catch (RestClientException e) {
			log.error("Erro inesperado" + e.getMessage());
			
			return null;
		}
	}

	@Override
	public String gerarCpf() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(token);
		
		var request = new HttpEntity<String>(headers);
		
		try {
			log.info("Requisitando a geração do CPF ");
			var response = restTemplate.exchange(geradorAppUrl, HttpMethod.GET, request, GeradorCPFDTO.class);			

			if ( HttpStatus.OK.equals(response.getStatusCode()) ) {
				var cpf = response.getBody().getNumber();
				log.info("Requisição finalizada com sucesso. CPF: " + cpf);
				return cpf;
			}
			
			log.info("Requisição retornou com um status não esperado. Retornando null.");
			return null;
		} catch(Exception e) {
			log.error("Erro inesperado" + e.getMessage());
			
			return null;
		}		
	}
}
