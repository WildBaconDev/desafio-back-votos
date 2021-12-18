package br.com.southsystem.votos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.southsystem.votos.model.Pauta;

@Repository
public interface PautaDAO extends JpaRepository<Pauta, Long> {

}
