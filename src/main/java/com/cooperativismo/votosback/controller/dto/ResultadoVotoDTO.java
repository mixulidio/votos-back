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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((opcao == null) ? 0 : opcao.hashCode());
		result = prime * result + ((pauta == null) ? 0 : pauta.hashCode());
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResultadoVotoDTO other = (ResultadoVotoDTO) obj;
		if (opcao != other.opcao)
			return false;
		if (pauta == null) {
			if (other.pauta != null)
				return false;
		} else if (!pauta.equals(other.pauta))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		return true;
	}

}
