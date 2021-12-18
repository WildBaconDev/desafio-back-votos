package br.com.southsystem.votos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.southsystem.votos.dto.PautaDTO;
import br.com.southsystem.votos.model.Pauta;

@Mapper
public interface PautaMapper {

	public static PautaMapper INSTANCE = Mappers.getMapper(PautaMapper.class);
	
	PautaDTO toDTO(Pauta pauta);
	Pauta toModel(PautaDTO pautaDTO);
}
