package com.molinari.cercalezione.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.molinari.cercalezione.domain.Lezione;
import com.molinari.cercalezione.domain.Tag;
import com.molinari.cercalezione.domain.dao.LezioneDao;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.label.LabelTestoPiccolo;
import com.molinari.utility.graphic.component.textfield.text.TextFieldTesto;

public class PannelloTrova extends PannelloBase {

	private static final long serialVersionUID = 1L;

	public PannelloTrova(Container contenitore) {
		super(contenitore);
	}
	
	@Override
	public void makeGUI(Container contenitorePadre) {
		this.setBackground(Color.red);
		LabelTestoPiccolo nomeLabel = new LabelTestoPiccolo("Tags", this);
		nomeLabel.setBounds(10, 10, 200, 30);
		
		TextFieldTesto tagsTF = new TextFieldTesto("Inserisci tag di ricerca separati da virgola", this);
		tagsTF.posizionaSottoA(nomeLabel, 0, 15);
		tagsTF.setSize(200, 30);
		
		ButtonBase button = new ButtonBase("Trova", this);
		button.posizionaSottoA(tagsTF, 0, 5);
		button.setSize(100, 30);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Lezione lezione = new Lezione();
//				lezione.setNome(numeroTF.getText());
//				int anno = getAnno(dataTF);
//				lezione.setAnno(anno);
//				lezione.setData(dataTF.getText());

				List<Tag> tags = Arrays.stream(tagsTF.getText().split(","))
				.map(t ->  {
					Tag tag = new Tag();
					tag.setDescrizione(t);
					return tag;
				}).collect(Collectors.toList());
				lezione.setTags(tags);
				
				LezioneDao lezioneDao = new LezioneDao(lezione);
				lezioneDao.find(Lezione.class, lezione);
				
			}
		});
	}
}
