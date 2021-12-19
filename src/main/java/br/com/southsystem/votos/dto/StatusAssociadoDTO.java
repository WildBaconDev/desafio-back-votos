package br.com.southsystem.votos.dto;

import br.com.southsystem.votos.model.AptoVotar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusAssociadoDTO {

	private AptoVotar status;
}
