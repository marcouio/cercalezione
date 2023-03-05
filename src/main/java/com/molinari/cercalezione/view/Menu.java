package com.molinari.cercalezione.view;

import java.awt.Point;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.molinari.cercalezione.Controllore;

public class Menu extends JMenuBar {

	private static final long serialVersionUID = 1L;
	private JCheckBoxMenuItem menuInserisci;
	private JCheckBoxMenuItem menuTrova;

	public Menu() {
		init();
	}

	private void init() {
		this.setBounds(0, 0, 1000, 700);

		// crea un menu
		final JMenu file = new JMenu("Azioni");
		this.add(file);

		menuInserisci = new JCheckBoxMenuItem("Inserisci Lezione");
		menuInserisci.setState(false);
		menuInserisci.addActionListener(arg0 -> inserisciPan());
		file.add(menuInserisci);

		menuTrova = new JCheckBoxMenuItem("Trova Lezione");
		menuTrova.setState(true);
		menuTrova.addActionListener(arg0 -> trovaPan());
		file.add(menuTrova);
		
		final JMenuItem chiudi = new JMenuItem("Chiudi");
		chiudi.addActionListener(e -> System.exit(0));
		file.add(chiudi);

		final JMenu help = new JMenu("Help");
		this.add(help);

		final JMenuItem info = new JMenuItem("Info");
		help.add(info);

		final JMenuItem manuale = new JMenuItem("Manuale");
		help.add(manuale);

	}
	
	private void setGrandezzaDelPannelloTrova(final FrameCercaLezione vista, final JPanel pannello) {
		final Double larghezza2 = Double.valueOf(vista.getCercaPan().getSize().getWidth());
		final Double altezza2 = Double.valueOf(vista.getCercaPan().getSize().getHeight());
		vista.setSize(larghezza2.intValue(), altezza2.intValue());
		
	}

	private void setGrandezzaDelPannelloInsert(final FrameCercaLezione vista) {
		final Double larghezza1 = Double.valueOf(vista.getCercaPan().getSize().getWidth());
		final Double altezza1 = Double.valueOf(vista.getCercaPan().getSize().getHeight());
		vista.setSize(larghezza1.intValue(), altezza1.intValue());
	}

	public void trovaPan() {
		final FrameCercaLezione vista = Controllore.getGeneralPanel();
		final JPanel pannelloCerca = vista.getCercaPan();
		final JPanel pannelloIns = vista.getInserisciPan();
		if (pannelloCerca.isVisible()) {
			selezionaPannelloIns(vista, pannelloCerca, pannelloIns);
		} else {
			selezionaPannelloCerca(vista, pannelloCerca, pannelloIns);
		}
		
		vista.invalidate();
		vista.repaint();
	}
	
	public void inserisciPan() {
		final FrameCercaLezione vista = Controllore.getGeneralPanel();
		final JPanel pannelloIns = vista.getInserisciPan();
		final JPanel pannelloCerca = vista.getCercaPan();
		// se il pannello è visibile lo nascondo e ridimensiono la vista
		// prendendo le dimensioni dell'altro pannello
		if (pannelloIns.isVisible()) {
			selezionaPannelloCerca(vista, pannelloCerca, pannelloIns);
		} else {
			selezionaPannelloIns(vista, pannelloCerca, pannelloIns);
		}
		vista.invalidate();
		vista.repaint();
	}

	private void selezionaPannelloCerca(final FrameCercaLezione vista, final JPanel pannelloCerca,
			final JPanel pannelloIns) {
		pannelloCerca.setVisible(true);
		pannelloIns.setVisible(false);
		menuInserisci.setState(false);
		menuTrova.setState(true);
		
		setGrandezzaDelPannelloTrova(vista, pannelloCerca);
		pannelloCerca.setLocation(new Point(0, 20));
	}

	private void selezionaPannelloIns(final FrameCercaLezione vista, final JPanel pannelloCerca,
			final JPanel pannelloIns) {
		pannelloCerca.setVisible(false);
		pannelloIns.setVisible(true);
		menuInserisci.setState(true);
		menuTrova.setState(false);
		
		setGrandezzaDelPannelloInsert(vista);
		pannelloIns.setLocation(new Point(0, 20));
	}
	
	

}
