package com.molinari.cercalezione;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.database.ConnectionPool;
import com.molinari.utility.database.ExecuteResultSet;
import com.molinari.utility.database.UtilDb;
import com.molinari.utility.graphic.component.alert.Alert;

public class Database {

	private static final String ROW_S = " row/s";
	private static Database singleton;
	public static final String DB_URL_WORKSPACE = "../cercalezioni.db";
	public static final String DB_URL_JAR = "./cercalezioni.db";
	private static String dburl = DB_URL_WORKSPACE;

	private Database() {

	}

	public static synchronized Database getSingleton() {

		if (singleton == null) {
			singleton = new Database();
		}
		return singleton;
	}

	public void generaDB() throws SQLException, IOException {
		new File(Database.dburl);

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("cercalezioni.db.sql");
		String text = new String(is.readAllBytes(), StandardCharsets.UTF_8);
		executeUpdate(text, ConnectionPool.getSingleton());
	}

	

	private void executeUpdate(String sql, final ConnectionPool cp) throws SQLException {
		cp.executeUpdate(sql);
	}

	/**
	 *
	 * Esegue un'istruzione SQL specificando come parametri il comando, la
	 * tabella, i campi di riferimento e clausole where. Non permette funzioni
	 * complesse.
	 *
	 * @param comando
	 * @param tabella
	 * @param campi
	 * @param clausole
	 * @return boolean
	 */
	public boolean eseguiIstruzioneSql(final String comando, final String tabella, final Map<String, String> campi,
			final Map<String, String> clausole) {
		return UtilDb.eseguiIstruzioneSql(comando, tabella, campi, clausole);
	}

	/**
	 * Il metodo esegue le stringhe di codice Sql inserite nel campo.
	 *
	 * @param sql
	 * @return HashMap<String, ArrayList>
	 */
	public Map<String, ArrayList<String>> terminaleSql(final String sql) {
		final HashMap<String, ArrayList<String>> nomi = new HashMap<>();
		if (startWith(sql, "S")) {
			try {

				return execute(sql, nomi);

			} catch (final SQLException e) {
				ControlloreBase.getLog().log(Level.SEVERE, "Operazione SQL non eseguita: " + sql, e);
				Alert.segnalazioneErroreGrave("Operazione SQL non eseguita:" + e.getMessage());
			}

		} else {

			try {
				int executeUpdate = ConnectionPool.getSingleton().executeUpdate(sql);
				sendAlert(sql, executeUpdate);
			} catch (final SQLException e) {
				ControlloreBase.getLog().log(Level.SEVERE, "Operazione SQL non eseguita: " + sql, e);
				Alert.segnalazioneErroreGrave("Operazione SQL non eseguita:" + e.getMessage());
			}
		}
		return nomi;
	}

	private boolean startWith(final String sql, String starting) {
		return starting.equalsIgnoreCase(sql.substring(0, 1));
	}

	private void sendAlert(String sql, long executeUpdate) {
		if (startWith(sql, "I")) {
			Alert.segnalazioneInfo("Insert " + executeUpdate + ROW_S);
		} else if (startWith(sql, "U")) {
			Alert.segnalazioneInfo("Update " + executeUpdate + ROW_S);
		} else if (startWith(sql, "D")) {
			Alert.segnalazioneInfo("Delete " + executeUpdate + ROW_S);
		}

	}

	private Map<String, ArrayList<String>> execute(final String sql, final HashMap<String, ArrayList<String>> nomi)
			throws SQLException {
		return new ExecuteResultSet<HashMap<String, ArrayList<String>>>() {

			@Override
			protected HashMap<String, ArrayList<String>> doWithResultSet(ResultSet rs) throws SQLException {
				final ResultSetMetaData rsmd = rs.getMetaData();
				final ArrayList<String> lista = new ArrayList<>();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					lista.add(rsmd.getColumnLabel(i + 1));
				}
				nomi.put("nomiColonne", lista);
				int z = 0;
				while (rs.next()) {
					final ArrayList<String> lista2 = new ArrayList<>();
					z++;
					riempiLista(rs, rsmd, lista2);
					nomi.put("row" + z, lista2);
				}
				return nomi;
			}

		}.execute(sql);
	}

	private void riempiLista(ResultSet rs, final ResultSetMetaData rsmd, final ArrayList<String> lista2)
			throws SQLException {
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
				lista2.add(Integer.toString(rs.getInt(i)));
			} else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
				lista2.add(Double.toString(rs.getInt(i)));
			} else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
				lista2.add(rs.getDate(i).toString());
			} else {
				lista2.add(rs.getString(i));
			}
		}
	}

	public static String getDburl() {
		return dburl;
	}

	public static void setDburl(String dburl) {
		Database.dburl = dburl;
	}

}