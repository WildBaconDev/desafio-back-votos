package br.com.southsystem.votos.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SolicitacaoPautaDTO {

	@NotBlank
	@ApiModelProperty(required = true)
	private String assunto;
	
	@NotBlank
	@ApiModelProperty(required = true)
	private String corpo;

}
