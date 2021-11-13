package com.molinari.cercalezione;

import com.molinari.utility.graphic.component.container.FrameBase;
import com.molinari.utility.graphic.component.container.PannelloBase;

public class GeneralFrame extends PannelloBase {

	private static final long serialVersionUID = 1L;
	private static final int MENU_HEIGHT = 20;
	private MyMenu menu;
	
	public GeneralFrame(FrameBase contenitore) {
		super(contenitore);
		setBounds(10, 10, contenitore.getWidth(), contenitore.getHeight());
		
		final MyMenu menuLoc = createMenu();
		
	}
	
	public MyMenu getMenu() {
		return menu;
	}

	public void setMenu(MyMenu menu) {
		this.menu = menu;
	}
	
	public MyMenu createMenu() {
		setMenu(new MyMenu());
		getMenu().setBounds(0, 0, this.getWidth(), MENU_HEIGHT);
		add(getMenu());
		getMenu().setVisible(true);
		return getMenu();
	}
	

}
