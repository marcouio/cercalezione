package com.molinari.cercalezione.domain.dao;

import com.molinari.cercalezione.domain.Tag;
import com.molinari.hibernate.dao.AbstractDao;

public class TagDao extends AbstractDao {

	private Tag entita;

	public TagDao(Tag entita) {
		this.setEntita(entita);
	}

	public Tag getEntita() {
		return entita;
	}

	public void setEntita(Tag entita) {
		this.entita = entita;
	}
}
