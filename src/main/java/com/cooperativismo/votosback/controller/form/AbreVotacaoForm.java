package com.cooperativismo.votosback.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AbreVotacaoForm {

	@NotNull
	@NotEmpty
	private Long pauta;

	private Integer tempo = 1;

	public Long getPauta() {
		return pauta;
	}

	public void setPauta(Long pauta) {
		this.pauta = pauta;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}
}
