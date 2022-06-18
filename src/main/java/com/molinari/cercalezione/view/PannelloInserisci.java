package com.molinari.cercalezione.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.molinari.cercalezione.domain.File;
import com.molinari.cercalezione.domain.Lezione;
import com.molinari.cercalezione.domain.Tag;
import com.molinari.cercalezione.domain.dao.LezioneDao;
import com.molinari.utility.GenericException;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.label.LabelTestoPiccolo;
import com.molinari.utility.graphic.component.textfield.date.TextFieldData;
import com.molinari.utility.graphic.component.textfield.text.TextFieldTesto;

public class PannelloInserisci extends PannelloBase {

	private static final long serialVersionUID = 1L;

	public PannelloInserisci(Container contenitore) {
		super(contenitore);
	}
	
	@Override
	public void makeGUI(Container contenitorePadre) {
		this.setBackground(Color.cyan);
		LabelTestoPiccolo nomeLabel = new LabelTestoPiccolo("Nome", this);
		nomeLabel.setLocation(0, 10);
		
		TextFieldTesto nameTF = new TextFieldTesto(this);
		nameTF.posizionaSottoA(nomeLabel, 0, 5);
		nameTF.setSize(true, 100, 6);
		
		LabelTestoPiccolo numeroLabel = new LabelTestoPiccolo("Numero", this);
		numeroLabel.posizionaSottoA(nameTF, 0, 5);
		
		TextFieldTesto numeroTF = new TextFieldTesto(this);
		numeroTF.posizionaSottoA(numeroLabel, 0, 5);
		numeroTF.setSize(true, 100, 6);

		LabelTestoPiccolo dataLabel = new LabelTestoPiccolo("Data", this);
		dataLabel.posizionaSottoA(numeroTF, 0, 5);
		
		TextFieldData dataTF = new TextFieldData("dd/MM/yyyy", this);
		dataTF.posizionaSottoA(dataLabel, 0, 5);
		dataTF.setSize(true, 100, 6);
		
		LabelTestoPiccolo tagsLabel = new LabelTestoPiccolo("Tags", this);
		tagsLabel.posizionaSottoA(dataTF, 0, 5);
		
		TextFieldTesto tagsTF = new TextFieldTesto(this);
		tagsTF.posizionaSottoA(tagsLabel, 0, 5);
		tagsTF.setSize(true, 100, 6);
		
		LabelTestoPiccolo fileLabel = new LabelTestoPiccolo("File", this);
		fileLabel.posizionaSottoA(tagsTF, 0, 5);
		
		TextFieldTesto fileTF = new TextFieldTesto(this);
		fileTF.posizionaSottoA(fileLabel, 0, 5);
		fileTF.setSize(true, 100, 6);
		
		ButtonBase button = new ButtonBase("Inserisci", this);
		button.posizionaSottoA(fileTF, 0, 5);
		button.setSize(true, 100, 6);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Lezione lezione = new Lezione();
					lezione.setNome(nameTF.getText());
					if(numeroTF.getText() != null) {
						lezione.setNumero(Integer.parseInt(numeroTF.getText()));
					}
					int anno = getAnno(dataTF);
					lezione.setAnno(anno);
					
					lezione.setData(dataTF.getText());
					
					List<Tag> tags = Arrays.stream(tagsTF.getText().split(","))
					.map(t ->  {
						Tag tag = new Tag();
						tag.setDescrizione(t.trim().toUpperCase());
						return tag;
					}).collect(Collectors.toList());
					lezione.setTags(tags);
					
					File file = new File();
					file.setNome(fileTF.getText());
					file.setPath(fileTF.getText());
					lezione.setFiles(Arrays.asList(file));
					LezioneDao lezioneDao = new LezioneDao(lezione);
					Object findLezione = lezioneDao.find(Lezione.class, lezione);
					if(findLezione == null) {
						lezioneDao.save();
						Alert.info("Lezione inserita", "Lezione inserita");
					}else {
						Alert.info("Lezione non inserita perché già presente", "Lezione non inserita");
					}
				}catch (Exception ex) {
					Alert.segnalazioneEccezione(ex, "Errore nel salvataggio della lezione");
				}
			}

			private int getAnno(TextFieldData dataTF) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat(dataTF.getFormat());
					Date date = sdf.parse(dataTF.getText());
					return Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
				} catch (ParseException e) {
					throw new GenericException(e);
				}
			}
		});
	}
}
