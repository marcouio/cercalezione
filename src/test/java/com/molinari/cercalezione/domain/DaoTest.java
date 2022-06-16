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
		lezEnt.setNome("Prova Lezione3");
		File entita = new File();
		entita.setNome("Prova File3");
		entita.setPath("Prova Path2");
		
		File entita2 = new File();
		entita2.setNome("Prova File4");
		entita2.setPath("Prova Path3");
		lezEnt.setFiles(Arrays.asList(entita, entita2));
		
		Tag tag1 = new Tag();
		tag1.setDescrizione("Ciao! sono tag");
		
		Tag tag2 = new Tag();
		tag2.setDescrizione("Ciao! sono tag");
		lezEnt.setTags(Arrays.asList(tag1, tag2));
		
		LezioneDao lezDao = new LezioneDao(lezEnt);
		lezDao.save();
		
	}
}
