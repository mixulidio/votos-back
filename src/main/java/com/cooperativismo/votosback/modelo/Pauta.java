package com.cooperativismo.votosback.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pauta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String titulo;

	private String descricao;

	@Column(nullable = false)
	private Integer assembleia;

	private LocalDateTime inicioVotacao;

	private LocalDateTime fimVotacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getAssembleia() {
		return assembleia;
	}

	public void setAssembleia(Integer assembleia) {
		this.assembleia = assembleia;
	}

	public LocalDateTime getInicioVotacao() {
		return inicioVotacao;
	}

	public void setInicioVotacao(LocalDateTime inicioVotacao) {
		this.inicioVotacao = inicioVotacao;
	}

	public LocalDateTime getFimVotacao() {
		return fimVotacao;
	}

	public void setFimVotacao(LocalDateTime fimVotacao) {
		this.fimVotacao = fimVotacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Pauta other = (Pauta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
