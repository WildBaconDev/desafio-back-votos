package br.com.southsystem.votos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.southsystem.votos.model.Sessao;

public interface SessaoDAO extends JpaRepository<Sessao, Long> {

}
