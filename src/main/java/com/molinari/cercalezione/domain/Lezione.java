package com.molinari.cercalezione.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.molinari.utility.commands.beancommands.AbstractOggettoEntita;

import java.util.List;


/**
 * The persistent class for the "Lezione" database table.
 * 
 */
@Entity
@Table(name="Lezione")
@NamedQuery(name="Lezione.findAll", query="SELECT l FROM Lezione l")
public class Lezione implements Serializable, AbstractOggettoEntita {
	private static final long serialVersionUID = 1L;

	@Column(name="anno")
	private int anno;

	@Column(name="data")
	private String data;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idLezione")
	private int idLezione;

	@Column(name="nome")
	private String nome;

	@Column(name="numero")
	private int numero;

	//bi-directional many-to-many association to File
	@ManyToMany
	@JoinTable(
	name="LezioneFile"
	, joinColumns={
			@JoinColumn(name="idLezione", referencedColumnName="idLezione")
		}
	, inverseJoinColumns={
			@JoinColumn(name="idFile", referencedColumnName="idFile")
		}
	)
	private List<File> files;

	//bi-directional many-to-many association to Tag
	@ManyToMany
	@JoinTable(
			name="LezioneTag"
			, joinColumns={
					@JoinColumn(name="idLezione", referencedColumnName="idLezione")
				}
			, inverseJoinColumns={
					@JoinColumn(name="idTag", referencedColumnName="idTag")
				}
			)
	private List<Tag> tags;

	public Lezione() {
	}

	public int getAnno() {
		return this.anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getIdLezione() {
		return this.idLezione;
	}

	public void setIdLezione(int idLezione) {
		this.idLezione = idLezione;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public List<File> getFiles() {
		return this.files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public List<Tag> getTags() {
		return this.tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getIdLezione());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setIdLezione(Integer.parseInt(idEntita));
	}
}