package com.molinari.cercalezione;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.molinari.cercalezione.view.FrameCercaLezione;
import com.molinari.utility.commands.AbstractCommand;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.controller.StarterBase;
import com.molinari.utility.database.ExecuteResultSet;
import com.molinari.utility.graphic.component.container.FrameBase;
import com.molinari.utility.messages.I18NManager;
import com.molinari.utility.servicesloader.LoaderLevel;

public class Controllore extends StarterBase {

	private FrameBase view;
	private FrameCercaLezione genPan;
	private String lookUsato;
	private static Controllore singleton;

	public Controllore() {
		//do nothing
	}
	
	public static Controllore getSingleton() {
		if (singleton == null) {
			singleton = new Controllore();
		}
		return singleton;
	}

	private static void verificaPresenzaDb() {
		final String sql = "SELECT * FROM Lezione";

		try {
			new ExecuteResultSet<Boolean>() {

				@Override
				protected Boolean doWithResultSet(ResultSet resulSet) throws SQLException {
					boolean next = resulSet.next();
					if(!next) {
						getLog().log(Level.INFO, "Il database esiste, non va creato");
					}
					return next;
				}
			}.execute(sql);
		} catch (final SQLException e) {
			getLog().log(Level.SEVERE, "Il database non c'è ancora, è da creare!", e);
			try {
				Database.getSingleton().generaDB();
			} catch (final SQLException | IOException e1) {
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
		ControlloreBase.getLog().setLevel(Level.INFO);
		Database.setDburl(Database.DB_URL_WORKSPACE);
		verificaPresenzaDb();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame.setBounds(10, 20, (int)screenSize.getWidth(), (int)screenSize.getHeight());
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		genPan = new FrameCercaLezione(frame);
		view = frame;
		view.setVisible(true);
		view.setTitle(I18NManager.getSingleton().getMessaggio("title"));

	}



	public FrameBase getGenView(){
		return view;
	}

	public FrameCercaLezione getGeneralFrameInner(){
		return this.genPan;
	}
	
	public static FrameCercaLezione getGeneralFrame(){
		Controllore starter = (Controllore) ControlloreBase.getSingleton().getStarter();
		return starter.getGeneralFrameInner();
	}
	
	public static Object getUtenteLogin(){
		return ControlloreBase.getSingleton().getUtenteLogin();
	}

	public void setConnectionClassName() {
		ControlloreBase.getSingleton().setConnectionClassName(ConnectionPoolCL.class.getName());
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