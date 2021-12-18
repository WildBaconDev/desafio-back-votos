package br.com.southsystem.votos.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.southsystem.votos.dto.ContagemVotosDTO;
import br.com.southsystem.votos.model.Voto;

@Repository
public interface VotoDAO extends JpaRepository<Voto, Long> {

	Optional<Voto> findByIdAssociadoAndSessaoId(Long idAssociado, Long sessaoId);

	@Query(value = "SELECT new br.com.southsystem.votos.dto.ContagemVotosDTO( " + 
			"  SUM( CASE WHEN voto.valorVoto = true THEN 1 ELSE 0 END ), " +
			"  SUM( CASE WHEN voto.valorVoto = false THEN 1 ELSE 0 END ) " +
			" ) FROM VOTO voto where voto.sessao.id = :idSessao GROUP BY voto.sessao.id ")
	ContagemVotosDTO contabilizarVotos(@Param("idSessao") Long idSessao);
	
}
