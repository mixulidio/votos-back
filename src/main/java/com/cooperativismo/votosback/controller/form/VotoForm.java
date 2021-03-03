package com.cooperativismo.votosback.controller.form;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import com.cooperativismo.votosback.modelo.OpcaoVotacao;

public class VotoForm {

	@NotNull
	private Long pauta;

	@NotNull
	private Integer associado;

	@NotNull
	@Enumerated(EnumType.STRING)
	private OpcaoVotacao opcao;

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
