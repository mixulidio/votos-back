package com.cooperativismo.votosback.controller;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cooperativismo.votosback.controller.dto.ResultadoVotoDTO;
import com.cooperativismo.votosback.controller.form.AbreVotacaoForm;
import com.cooperativismo.votosback.controller.form.VotoForm;
import com.cooperativismo.votosback.exeption.ValidationException;
import com.cooperativismo.votosback.modelo.OpcaoVotacao;
import com.cooperativismo.votosback.modelo.Pauta;

@SpringBootTest
@AutoConfigureMockMvc
class AssembleiaTest {

	@Autowired
	public VotosController votosController;

	@Autowired
	private PautaController pautaController;

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void novaPautaVerificaErro400() throws Exception {
		URI uri = new URI("/novapauta" );
		String jsonBody =  "{\"assembleia\" : 3 }";
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON))
		 .andExpect(MockMvcResultMatchers
                .status()
                .is(400));;
	}
	
	
	@Test
	public void novaPautaNaoDeveIncluir()  {
		Pauta pauta = new Pauta();
		try {
			pautaController.novaPauta(pauta);
			fail("Não econtrou ValidationException");
		} catch (ValidationException e) {
		}
	}

	@Test
	public void novaPauta() throws ValidationException  {
		novaPautaoIncluir();
	}

	public Long novaPautaoIncluir() throws ValidationException  {
		Pauta pauta = new Pauta();
		pauta.setAssembleia(1);
		pauta.setTitulo("Pauta Teste");
		ResponseEntity<Pauta> novaPauta = pautaController.novaPauta(pauta);
		Pauta pautaRet = novaPauta.getBody();
		return pautaRet.getId();
	}

	@Test
	public void abreVotacaoPautaDeveSerInformada() {
		AbreVotacaoForm abreVotacaoForm = new AbreVotacaoForm();
		try {
			pautaController.abreVotacao(abreVotacaoForm);
			fail("Não econtrou ValidationException");
		} catch (ValidationException e) {
			assertEquals("Pauta deve ser informada.", e.getMessage());
		}
	}

	@Test
	public void abreVotacaoTempoDeveserMaiorQueZero() {
		AbreVotacaoForm abreVotacaoForm = new AbreVotacaoForm();
		abreVotacaoForm.setPauta((long) 10); 
		abreVotacaoForm.setTempo(0);
		try {
			pautaController.abreVotacao(abreVotacaoForm);
			fail("Não econtrou ValidationException");
		} catch (ValidationException e) {
			assertEquals("Tempo deve ser maior que Zero.", e.getMessage());
		}
	}

	@Test
	public void abreVotacaoPautaNaoExiste404() throws Exception{
			URI uri = new URI("/abreVotacao" );
			String jsonBody =  "{\"pauta\" : 99999 }";
			mockMvc.perform(MockMvcRequestBuilders
					.post(uri)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON))
			 .andExpect(MockMvcResultMatchers
	                .status()
	                .is(404));;
	}

	@Test
	public void abreVotacaoComTempoDefault() throws ValidationException {
		AbreVotacaoForm abreVotacaoForm = new AbreVotacaoForm();
		abreVotacaoForm.setPauta((long) 1);
		ResponseEntity<Pauta> abreVotacao = pautaController.abreVotacao(abreVotacaoForm);
		LocalDateTime inicioVotacao = abreVotacao.getBody().getInicioVotacao();
		LocalDateTime fimVotacao = abreVotacao.getBody().getFimVotacao();
		assertTrue(fimVotacao.minusMinutes(1).isEqual(inicioVotacao));
	}

	@Test
	void votarPautaNaoEncontrada() {
		VotoForm votoForm = new VotoForm();
		votoForm.setPauta(Long.MAX_VALUE);
		votoForm.setAssociado(1);
		votoForm.setOpcao(OpcaoVotacao.SIM);
		try {
			votosController.votar(votoForm);
			fail("Não econtrou ValidationException");
		} catch (ValidationException e) {
			assertEquals("Pauta não encontrada.", e.getMessage());
		}
	}

	@Test
	void votarPautaNaoEstaAbertaParaVotacao() throws ValidationException {
		Long novaPautaoIncluir = novaPautaoIncluir();
		VotoForm votoForm = new VotoForm();
		votoForm.setPauta(novaPautaoIncluir);
		votoForm.setAssociado(1);
		votoForm.setOpcao(OpcaoVotacao.SIM);
		try {
			votosController.votar(votoForm);
			fail("Não econtrou ValidationException");
		} catch (ValidationException e) {
			assertEquals("Pauta não está aberta para votação.", e.getMessage());
		}
	}
	
	@Test
	public void  contabilizarResultadoVotosPautaInformePautacorretamente() {
		try {
			votosController.contabilizarResultadoVotosPauta((long) -1);
			fail("Não econtrou ValidationException");
		} catch (ValidationException e) {
			assertEquals("Informe a Pauta corretamente.", e.getMessage());
		}
	}

	@Test
	public void contabilizarResultadoVotosPauta() throws ValidationException {
		// abre votacao
		Long novaPautaoIncluir = novaPautaoIncluir();
		AbreVotacaoForm abreVotacaoForm = new AbreVotacaoForm();
		abreVotacaoForm.setPauta(novaPautaoIncluir);
		abreVotacaoForm.setTempo(10);
		pautaController.abreVotacao(abreVotacaoForm);

		Random random = new Random();

		// votos
		int votosSim = random.nextInt(100) + 1;
		int votosNao = random.nextInt(100) + 1;
		for (int i = 1; i <= votosSim; i++) {
			VotoForm votoForm = new VotoForm();
			votoForm.setPauta(novaPautaoIncluir);
			votoForm.setAssociado(i);
			votoForm.setOpcao(OpcaoVotacao.SIM);
			votosController.votar(votoForm);
		}
		for (int i = votosSim + 1; i <= votosSim + votosNao; i++) {
			VotoForm votoForm = new VotoForm();
			votoForm.setPauta(novaPautaoIncluir);
			votoForm.setAssociado(i);
			votoForm.setOpcao(OpcaoVotacao.NAO);
			votosController.votar(votoForm);
		}

		// resultado
		ResponseEntity<List<ResultadoVotoDTO>> contabilizarResultadoVotosPauta = votosController
				.contabilizarResultadoVotosPauta(novaPautaoIncluir);

		List<ResultadoVotoDTO> body = contabilizarResultadoVotosPauta.getBody();
		for (ResultadoVotoDTO resultadoVotoDTO : body) {
			System.out.println("------");
			System.out.println(resultadoVotoDTO.getOpcao());
			System.out.println(resultadoVotoDTO.getQuantidade());
		}

		List<ResultadoVotoDTO> resultadoVotoDTO = new ArrayList<ResultadoVotoDTO>();
		resultadoVotoDTO.add(new ResultadoVotoDTO(novaPautaoIncluir, OpcaoVotacao.NAO, (long) votosNao));
		resultadoVotoDTO.add(new ResultadoVotoDTO(novaPautaoIncluir, OpcaoVotacao.SIM, (long) votosSim));

		assertArrayEquals(resultadoVotoDTO.toArray(), contabilizarResultadoVotosPauta.getBody().toArray());
	}
	
}
