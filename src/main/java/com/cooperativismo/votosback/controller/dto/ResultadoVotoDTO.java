package com.cooperativismo.votosback.controller.dto;

import com.cooperativismo.votosback.modelo.OpcaoVotacao;

public class ResultadoVotoDTO {

	private Long pauta;

	private OpcaoVotacao opcao;

	private Long quantidade;

	public ResultadoVotoDTO() {
	}

	public ResultadoVotoDTO(Long pauta, OpcaoVotacao opcao, Long quantidade) {
		this.pauta = pauta;
		this.opcao = opcao;
		this.quantidade = quantidade;
	}

	public Long getPauta() {
		return pauta;
	}

	public void setPauta(Long pauta) {
		this.pauta = pauta;
	}

	public OpcaoVotacao getOpcao() {
		return opcao;
	}

	public void setOpcao(OpcaoVotacao opcao) {
		this.opcao = opcao;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

}
