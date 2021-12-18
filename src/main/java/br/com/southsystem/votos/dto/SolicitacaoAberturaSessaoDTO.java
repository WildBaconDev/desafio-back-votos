package br.com.southsystem.votos.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SolicitacaoAberturaSessaoDTO {

	@NotNull
	@ApiModelProperty(required = true)
	private Long idPauta;
	
	@ApiModelProperty(allowEmptyValue = true)
	@Min(value = 1)
	private Integer minutosSessaoAberta = 1;
	
}
