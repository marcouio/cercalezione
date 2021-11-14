package com.molinari.cercalezione.view;

import java.awt.Color;
import java.awt.Container;

import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.label.LabelTestoPiccolo;
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
		nomeLabel.setBounds(10, 10, 200, 30);
		
		TextFieldTesto nameTF = new TextFieldTesto(this);
		nameTF.posizionaSottoA(nomeLabel, 0, 15);
		nameTF.setSize(100, 30);
	}
}
