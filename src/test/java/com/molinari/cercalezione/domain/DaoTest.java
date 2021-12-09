package com.molinari.cercalezione.domain;

import java.util.Arrays;

import org.junit.Test;

import com.molinari.cercalezione.Controllore;
import com.molinari.cercalezione.domain.dao.LezioneDao;

public class DaoTest {

	@Test
	public void testDao() {
		Controllore.getSingleton().setConnectionClassName();
		Lezione lezEnt = new Lezione();
		lezEnt.setAnno(2021);
		lezEnt.setData("07/12/2021");
		lezEnt.setNome("Prova Lezione");
		File entita = new File();
		entita.setNome("Prova File");
		entita.setPath("Prova Path");
		lezEnt.setFiles(Arrays.asList(entita));
		LezioneDao lezDao = new LezioneDao(lezEnt);
		lezDao.saveOrUpdate(lezEnt);
		
	}
}
