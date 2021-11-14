package com.molinari.cercalezione.view;

import java.awt.Color;
import java.awt.Container;

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
		tagsTF.setSize(100, 30);
	}
}
