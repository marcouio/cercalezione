package com.molinari.cercalezione;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.molinari.cercalezione.view.FrameCercaLezione;
import com.molinari.utility.commands.AbstractCommand;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.controller.Starter;
import com.molinari.utility.controller.StarterBase;
import com.molinari.utility.database.ExecuteResultSet;
import com.molinari.utility.graphic.PercentageDimension;
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

		try {
			execResultSet().execute("SELECT * FROM Lezione");
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

	private static ExecuteResultSet<Boolean> execResultSet() {
		return new ExecuteResultSet<Boolean>() {

			@Override
			protected Boolean doWithResultSet(ResultSet resulSet) throws SQLException {
				boolean next = resulSet.next();
				getLog().log(Level.INFO, "Il database esiste, non va creato");
				return next;
			}
		};
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
		frame.setLocation(10, 20);
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
	
	public static FrameCercaLezione getGeneralPanel(){
		Controllore starter = (Controllore) ControlloreBase.getSingleton().getStarter();
		return starter.getGeneralFrameInner();
	}
	
	public static FrameBase getGeneralFrame(){
		Controllore starter = (Controllore) ControlloreBase.getSingleton().getStarter();
		return starter.getGenView();
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

	@Override
	public PercentageDimension getPercentageDimension() {
		return new PercentageDimension(true, 16, 60);
	}

	@Override
	public Starter createInstance(Object... args) {
		return new Controllore();
	}
	
	public static void main(final String[] args) {
		ControlloreBase.getLog().setLevel(Level.SEVERE);
		ControlloreBase.getSingleton().myMain(ControlloreBase.getSingleton(), "myApplication");
	}

}