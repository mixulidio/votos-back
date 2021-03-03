package com.cooperativismo.votosback.controller.dto;

import com.cooperativismo.votosback.modelo.OpcaoVotacao;
import com.cooperativismo.votosback.modelo.Votos;

public class VotoDTO {

	private Long pauta;

	private Integer associado;

	private OpcaoVotacao opcao;

	public VotoDTO() {
	}

	public VotoDTO(Votos votoRet) {
		this.pauta = votoRet.getPauta().getId();
		this.associado = votoRet.getAssociado();
		this.opcao = votoRet.getOpcao();
	}

	public Long getPauta() {
		return pauta;
	}

	public void setPauta(Long pauta) {
		this.pauta = pauta;
	}

	public Integer getAssociado() {
		return associado;
	}

	public void setAssociado(Integer associado) {
		this.associado = associado;
	}

	public OpcaoVotacao getOpcao() {
		return opcao;
	}

	public void setOpcao(OpcaoVotacao opcao) {
		this.opcao = opcao;
	}

}
