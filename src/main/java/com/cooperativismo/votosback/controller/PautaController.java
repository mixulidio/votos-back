package com.cooperativismo.votosback.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cooperativismo.votosback.modelo.Pauta;
import com.cooperativismo.votosback.repository.PautaRepository;

@RestController
public class PautaController {

	@Autowired
	private PautaRepository pautaRepository;

	@PostMapping(value = "/novapauta")
	@Transactional
	public ResponseEntity<Pauta> novaPauta(@RequestBody Pauta pauta) {
		Pauta savePauta = pautaRepository.save(pauta);
		return new ResponseEntity<Pauta>(savePauta, HttpStatus.OK);
	}

}
