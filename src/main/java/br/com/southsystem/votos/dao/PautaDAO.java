package br.com.southsystem.votos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.southsystem.votos.model.Pauta;

public interface PautaDAO extends JpaRepository<Pauta, Long> {

}
