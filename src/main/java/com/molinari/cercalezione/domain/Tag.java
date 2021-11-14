package com.molinari.cercalezione.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the "Tag" database table.
 * 
 */
@Entity
@Table(name="\"Tag\"")
@NamedQuery(name="Tag.findAll", query="SELECT t FROM Tag t")
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="\"descrizione\"")
	private String descrizione;

	@Id
	@Column(name="\"idTag\"")
	private int idTag;

	//bi-directional many-to-many association to Lezione
	@ManyToMany
	@JoinTable(
		name="\"LezioneTag\""
		, joinColumns={
				@JoinColumn(name="\"idTag\"", referencedColumnName="\"idTag\"")
			}
		, inverseJoinColumns={
				@JoinColumn(name="\"idLezione\"", referencedColumnName="\"idLezione\"")
			}
		)
	private List<Lezione> leziones;

	public Tag() {
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getIdTag() {
		return this.idTag;
	}

	public void setIdTag(int idTag) {
		this.idTag = idTag;
	}

	public List<Lezione> getLeziones() {
		return this.leziones;
	}

	public void setLeziones(List<Lezione> leziones) {
		this.leziones = leziones;
	}

}