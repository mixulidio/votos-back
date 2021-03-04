package com.cooperativismo.votosback.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cooperativismo.votosback.controller.dto.ResultadoVotoDTO;
import com.cooperativismo.votosback.controller.dto.VotoDTO;
import com.cooperativismo.votosback.controller.form.VotoForm;
import com.cooperativismo.votosback.exeption.ValidationException;
import com.cooperativismo.votosback.modelo.Pauta;
import com.cooperativismo.votosback.modelo.Votos;
import com.cooperativismo.votosback.repository.PautaRepository;
import com.cooperativismo.votosback.repository.VotosRepository;

@RestController
public class VotosController {

	@Autowired
	private VotosRepository votosRepository;

	@Autowired
	private PautaRepository pautaRepository;

	public LocalDateTime rightNow() {
		return LocalDateTime.now();
	}

	@PostMapping(value = "voto")
	@Transactional
	public ResponseEntity<VotoDTO> votar(@RequestBody @Valid VotoForm votoForm) throws ValidationException {
		Optional<Pauta> optPauta = pautaRepository.findById(votoForm.getPauta());
		validaVotacaoAberta(rightNow(), optPauta);

		validaJaVotado(votoForm);

		Votos voto = new Votos(votoForm, optPauta.get());
		Votos votoRet = votosRepository.save(voto);

		VotoDTO votoDTO = new VotoDTO(votoRet);

		return new ResponseEntity<VotoDTO>(votoDTO, HttpStatus.OK);
	}

	private void validaJaVotado(VotoForm votoForm) throws ValidationException {
		List<Votos> consultaVotoAssociado = votosRepository.listaVotoAssociado(votoForm.getAssociado(),	votoForm.getPauta());
		if (consultaVotoAssociado != null && !consultaVotoAssociado.isEmpty()) {
			throw new ValidationException("Pauta Já foi votada pelo associado.");
		}
	}

	private void validaVotacaoAberta(LocalDateTime rightNow, Optional<Pauta> optPauta) throws ValidationException {
		if (!optPauta.isPresent()) {
			throw new ValidationException("Pauta não encontrada.");
		}
		
		Pauta pautaRet = optPauta.get();
		if (pautaRet == null) {
			throw new ValidationException("Pauta não encontrada.");
		}

		if (pautaRet.getInicioVotacao() == null || pautaRet.getFimVotacao() == null) {
			throw new ValidationException("Pauta não está aberta para votação.");
		}

		if (rightNow.isBefore(pautaRet.getInicioVotacao())) {
			throw new ValidationException("Votação não iniciada.");
		}

		if (rightNow.isAfter(pautaRet.getFimVotacao())) {
			throw new ValidationException("Votação já encerrada.");
		}
	}

	@GetMapping(value = "resulltado/{pauta}")
	public ResponseEntity<List<ResultadoVotoDTO>> contabilizarResultadoVotosPauta(@PathVariable Long pauta)	throws ValidationException {
		if (pauta == null || pauta <= 0) {
			throw new ValidationException("Informe a Pauta corretamente.");
		}

		List<ResultadoVotoDTO> resultado = votosRepository.contabilizarResultadoVotosPauta(pauta);
		return new ResponseEntity<List<ResultadoVotoDTO>>(resultado, HttpStatus.OK);
	}
}
