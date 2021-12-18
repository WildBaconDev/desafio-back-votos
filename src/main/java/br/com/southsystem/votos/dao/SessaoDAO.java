package br.com.southsystem.votos.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.southsystem.votos.model.Sessao;

@Repository
public interface SessaoDAO extends JpaRepository<Sessao, Long> {

	Optional<Sessao> findByPautaId(Long id);
	
}
