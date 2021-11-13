package com.molinari.cercalezione;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.molinari.utility.commands.AbstractCommand;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.controller.StarterBase;
import com.molinari.utility.database.ExecuteResultSet;
import com.molinari.utility.graphic.component.container.FrameBase;
import com.molinari.utility.messages.I18NManager;
import com.molinari.utility.servicesloader.LoaderLevel;

public class Controllore extends StarterBase {

	private static final String GUEST = "guest";
	private FrameBase view;
	private GeneralFrame genPan;
	private String lookUsato;

	public Controllore() {
		//do nothing
	}

	private static void verificaPresenzaDb() {
		final String sql = "SELECT * FROM Lezione";

		try {
			new ExecuteResultSet<Boolean>() {

				@Override
				protected Boolean doWithResultSet(ResultSet resulSet) throws SQLException {
					return resulSet.next();
				}
			}.execute(sql);
		} catch (final SQLException e) {
			getLog().log(Level.SEVERE, "Il database non c'è ancora, è da creare!", e);
			try {
				Database.getSingleton().generaDB();
			} catch (final SQLException e1) {
				getLog().log(Level.SEVERE, "Error on Db creation: " + e1.getMessage(), e1);
				File file = new File(Database.getDburl());
				System.out.println(file.getAbsolutePath());
				file.deleteOnExit();
			}
			getLog().severe(e.getMessage());
		}
	}


	public static Logger getLog() {
		return ControlloreBase.getLog();
	}

	public static boolean invocaComando(final AbstractCommand comando) {
		return ControlloreBase.getSingleton().getCommandManager().invocaComando(comando);
	}


	public FrameBase getView() {
		return view;
	}

	@Override
	public void start(FrameBase frame) {
		
		setConnectionClassName();
		ControlloreBase.getLog().setLevel(Level.SEVERE);
		Database.setDburl(Database.DB_URL_WORKSPACE);
		verificaPresenzaDb();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame.setBounds(10, 20, (int)screenSize.getWidth(), (int)screenSize.getHeight());
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		genPan = new GeneralFrame(frame);
		view = frame;
		view.setVisible(true);
		view.setTitle(I18NManager.getSingleton().getMessaggio("title"));

	}



	public FrameBase getGenView(){
		return view;
	}

	public GeneralFrame getGeneralFrameInner(){
		return this.genPan;
	}
	
	public static GeneralFrame getGeneralFrame(){
		Controllore starter = (Controllore) ControlloreBase.getSingleton().getStarter();
		return starter.getGeneralFrameInner();
	}
	
	public static Object getUtenteLogin(){
		return ControlloreBase.getSingleton().getUtenteLogin();
	}

	public void setConnectionClassName() {
		getControllore().setConnectionClassName(ConnectionPoolGGS.class.getName());
	}

	public String getLookUsato() {
		return lookUsato;
	}

	public void setLookUsato(String lookUsato) {
		this.lookUsato = lookUsato;
	}
	
	@Override
	public LoaderLevel getLevel() {
		return LoaderLevel.IMPLEMENTED;
	}

}