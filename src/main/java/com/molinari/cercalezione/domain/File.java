package com.molinari.cercalezione.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the "File" database table.
 * 
 */
@Entity
@Table(name="File")
@NamedQuery(name="File.findAll", query="SELECT f FROM File f")
public class File implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idFile", nullable=false)
	private int idFile;

	@Column(name="nome", nullable=false, length=2000000000)
	private String nome;

	@Column(name="path", nullable=false, length=2000000000)
	private String path;

	//bi-directional many-to-many association to Lezione
	@ManyToMany
	@JoinTable(
	name="LezioneFile"
	, joinColumns={
			@JoinColumn(name="idFile", referencedColumnName="idFile")
		}
	, inverseJoinColumns={
			@JoinColumn(name="idLezione", referencedColumnName="idLezione")
		}
	)
	private List<Lezione> leziones;

	public File() {
	}

	public int getIdFile() {
		return this.idFile;
	}

	public void setIdFile(int idFile) {
		this.idFile = idFile;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<Lezione> getLeziones() {
		return this.leziones;
	}

	public void setLeziones(List<Lezione> leziones) {
		this.leziones = leziones;
	}

}