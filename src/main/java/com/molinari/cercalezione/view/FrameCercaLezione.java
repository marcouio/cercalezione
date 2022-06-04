package com.molinari.cercalezione.view;

import com.molinari.utility.graphic.component.container.FrameBase;
import com.molinari.utility.graphic.component.container.PannelloBase;

public class FrameCercaLezione extends PannelloBase {

	private static final long serialVersionUID = 1L;
	private static final int MENU_HEIGHT = 20;
	private Menu menu;
	private PannelloInserisci inserisciPan;
	private PannelloTrova cercaPan;
	
	public FrameCercaLezione(FrameBase contenitore) {
		super(contenitore);
		setBounds(10, 10, contenitore.getWidth(), contenitore.getHeight());
		
		final Menu menuLoc = createMenu();
		inserisciPan = new PannelloInserisci(this);
		getInserisciPan().posizionaADestraDi(menuLoc, 0, 10);
		getInserisciPan().setVisible(false);
		cercaPan = new PannelloTrova(this);
		getCercaPan().posizionaSottoA(menuLoc, 0, 10);
	}
	
	
	public Menu createMenu() {
		setMenu(new Menu());
		getMenu().setBounds(0, 0, this.getWidth(), MENU_HEIGHT);
		add(getMenu());
		getMenu().setVisible(true);
		return getMenu();
	}

	public PannelloTrova getCercaPan() {
		return cercaPan;
	}

	public void setCercaPan(PannelloTrova cercaPan) {
		this.cercaPan = cercaPan;
	}

	public PannelloInserisci getInserisciPan() {
		return inserisciPan;
	}

	public void setInserisciPan(PannelloInserisci inserisciPan) {
		this.inserisciPan = inserisciPan;
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
