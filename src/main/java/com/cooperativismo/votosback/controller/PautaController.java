package com.cooperativismo.votosback.controller;

import java.time.LocalDateTime;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cooperativismo.votosback.controller.form.AbreVotacaoForm;
import com.cooperativismo.votosback.exeption.ValidationException;
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

	/**
	 * Atualiza a data de inicio e Fim da Pauta
	 */
	@PostMapping(value = "/abrevotacao")
	@Transactional
	public ResponseEntity<Pauta> abreVotacao(@RequestBody @Valid AbreVotacaoForm abreVotacaoForm)
			throws ValidationException {

		validaAbreVotaca(abreVotacaoForm);

		Pauta pautaReturn = pautaRepository.getOne(abreVotacaoForm.getPauta());
		LocalDateTime rightNow = LocalDateTime.now();
		LocalDateTime rightNowPlusMinutes = LocalDateTime.now().plusMinutes(abreVotacaoForm.getTempo());
		pautaReturn.setInicioVotacao(rightNow);
		pautaReturn.setFimVotacao(rightNowPlusMinutes);

		Pauta savePauta = pautaRepository.save(pautaReturn);
		return new ResponseEntity<Pauta>(savePauta, HttpStatus.OK);
	}

	private void validaAbreVotaca(AbreVotacaoForm abreVotacaoForm) throws ValidationException {
		if (abreVotacaoForm.getPauta() == null) {
			throw new ValidationException("Pauta deve ser informada");
		}

		if (abreVotacaoForm.getTempo() != null && abreVotacaoForm.getTempo() <= 0) {
			throw new ValidationException("Tempo deve ser maior que Zero");
		}
	}

}
