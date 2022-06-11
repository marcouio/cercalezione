package com.molinari.cercalezione.domain.dao;

import java.util.stream.Stream;

import org.hibernate.Session;

import com.molinari.cercalezione.domain.Lezione;
import com.molinari.hibernate.dao.AbstractDao;
import com.molinari.hibernate.dao.TransactionalTask;

public class LezioneDao extends AbstractDao {

	private Lezione entita;
	
	public LezioneDao(Lezione entita) {
		this.entita = entita;
	}
	
	public void save() {
		
		TransactionalTask t = new TransactionalTask() {
			
			@Override
			public void execute(Session session) {
				session.saveOrUpdate(entita);
				Stream.ofNullable(entita.getFiles())
				.map(list -> list.stream()).findFirst()
				.orElse(Stream.empty())
				.forEach(f -> session.saveOrUpdate(f));
				
				Stream.ofNullable(entita.getTags())
				.map(list -> list.stream()).findFirst()
				.orElse(Stream.empty())
				.forEach(t -> session.saveOrUpdate(t));
			}
		};
		executeOnTransaction(t);
	}
}
