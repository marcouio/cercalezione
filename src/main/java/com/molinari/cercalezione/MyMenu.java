package com.molinari.cercalezione;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MyMenu extends JMenuBar {

	private static final long serialVersionUID = 1L;

	public MyMenu() {
		init();
	}

	private void init() {
		this.setBounds(0, 0, 1000, 20);

		// crea un menu
		final JMenu file = new JMenu("Azioni");
		this.add(file);

		final JMenuItem inserisci = new JMenuItem("Inserisci Lezione");
		inserisci.addActionListener(e -> apriInserisci());
		file.add(inserisci);
		
		final JMenuItem trova = new JMenuItem("Trova Lezione");
		trova.addActionListener(e -> apriTrova());
		file.add(trova);

		final JMenuItem chiudi = new JMenuItem("Chiudi");
		chiudi.addActionListener(e -> System.exit(0));
		file.add(chiudi);

		final JMenu help = new JMenu("Help");
		add(help);

		final JMenuItem info = new JMenuItem("Info");
		help.add(info);

		final JMenuItem manuale = new JMenuItem("Manuale");
		help.add(manuale);

	}

	private Object apriTrova() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object apriInserisci() {
		// TODO Auto-generated method stub
		return null;
	}

}
