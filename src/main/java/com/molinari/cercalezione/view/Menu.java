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

	public Menu() {
		init();
	}

	private void init() {
		this.setBounds(0, 0, 1000, 20);

		// crea un menu
		final JMenu file = new JMenu("Azioni");
		this.add(file);

		final JCheckBoxMenuItem gestore = new JCheckBoxMenuItem("Inserisci Lezione");
		gestore.setState(true);
		gestore.addActionListener(arg0 -> inserisciPan());
		file.add(gestore);

		final JCheckBoxMenuItem player = new JCheckBoxMenuItem("Trova Lezione");
		player.setState(true);
		player.addActionListener(arg0 -> trovaPan());
		file.add(player);
		
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
		final FrameCercaLezione vista = Controllore.getGeneralFrame();
		final JPanel pannello = vista.getCercaPan();
		final JPanel pannelloIns = vista.getInserisciPan();
		if (pannello.isVisible()) {
			pannello.setVisible(false);
			pannelloIns.setVisible(true);
			
			setGrandezzaDelPannelloInsert(vista);
			vista.getCercaPan().setLocation(new Point(0, 20));
		} else {
			pannello.setVisible(true);
			pannelloIns.setVisible(false);
			
			setGrandezzaDelPannelloTrova(vista, pannello);
			pannello.setLocation(new Point(0, 20));
		}
		
		vista.invalidate();
		vista.repaint();
	}
	
	public void inserisciPan() {
		final FrameCercaLezione vista = Controllore.getGeneralFrame();
		final JPanel pannello = vista.getInserisciPan();
		final JPanel pannelloCerca = vista.getCercaPan();
		// se il pannello è visibile lo nascondo e ridimensiono la vista
		// prendendo le dimensioni dell'altro pannello
		if (pannello.isVisible()) {
			pannello.setVisible(false);
			pannelloCerca.setVisible(true);
			setGrandezzaDelPannelloInsert(vista);
			vista.getInserisciPan().setLocation(new Point(0, 20));
		} else {
			pannello.setVisible(true);
			pannelloCerca.setVisible(false);
			setGrandezzaDelPannelloInsert(vista);
			vista.getInserisciPan().setLocation(new Point(0, 20));
			
		}
		vista.invalidate();
		vista.repaint();
	}

}
