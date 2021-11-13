package com.molinari.cercalezione;

import com.molinari.utility.graphic.component.container.FrameBase;
import com.molinari.utility.graphic.component.container.PannelloBase;

public class GeneralFrame extends PannelloBase {

	private static final long serialVersionUID = 1L;

	public GeneralFrame(FrameBase contenitore) {
		super(contenitore);
		setBounds(10, 10, contenitore.getWidth(), contenitore.getHeight());
		
	}
	

}
