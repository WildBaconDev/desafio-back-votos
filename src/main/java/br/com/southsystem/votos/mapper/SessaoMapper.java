package br.com.southsystem.votos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.southsystem.votos.dto.SessaoDTO;
import br.com.southsystem.votos.model.Sessao;

@Mapper
public interface SessaoMapper {

	public static SessaoMapper INSTANCE = Mappers.getMapper(SessaoMapper.class);
	
	SessaoDTO toDTO(Sessao sessao);
	Sessao toModel(SessaoDTO sessaoDTO);
}
