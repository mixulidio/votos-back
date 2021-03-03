package com.cooperativismo.votosback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cooperativismo.votosback.controller.dto.ResultadoVotoDTO;
import com.cooperativismo.votosback.modelo.Votos;

public interface VotosRepository extends JpaRepository<Votos, Long> {

	@Query("SELECT v FROM Votos v WHERE v.associado = :associado and v.pauta.id = :pauta")
	List<Votos> listaVotoAssociado(@Param("associado") Integer associado, @Param("pauta") Long pauta);

	@Query("SELECT new com.cooperativismo.votosback.controller.dto.ResultadoVotoDTO(v.pauta.id, v.opcao, count(v.opcao)) FROM Votos v WHERE v.pauta.id = :pauta GROUP BY v.pauta.id, v.opcao ORDER BY v.opcao")
	List<ResultadoVotoDTO> contabilizarResultadoVotosPauta(@Param("pauta") Long pauta);

}
