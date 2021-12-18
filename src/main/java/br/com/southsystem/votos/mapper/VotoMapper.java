package br.com.southsystem.votos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.southsystem.votos.dto.VotoDTO;
import br.com.southsystem.votos.model.Voto;

@Mapper
public interface VotoMapper {

	public static VotoMapper INSTANCE = Mappers.getMapper( VotoMapper.class );
	
	VotoDTO toDTO(Voto voto);
	Voto toModel(VotoDTO votoDTO);
	
}
