package com.molinari.cercalezione;

import java.io.File;
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

	private static final String ID_UTENTE_INTEGER_NOT_NULL = " \"idUtente\"	INTEGER NOT NULL, ";
	private static final String NOME_TEXT_NOT_NULL = " \"nome\"	TEXT NOT NULL, ";
	private static final String FOREIGN_KEY_ID_UTENTE_REFERENCES_UTENTI_ID_UTENTE = " FOREIGN KEY(\"idUtente\") REFERENCES \"utenti\"(\"idUtente\") ";
	private static final String WHERE = " where ";
	private static final String ROW_S = " row/s";
	private static final String AND = " AND ";
	private static final String FROM = " FROM ";
	private static final String YYYY_MM_DD = "yyyy/MM/dd";
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

	public void generaDB() throws SQLException {
		new File(Database.dburl);

		String sql = "CREATE TABLE \"utenti\" (\"idUtente\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , \"nome\" TEXT NOT NULL , \"cognome\" TEXT NOT NULL , \"username\" TEXT NOT NULL  UNIQUE , \"password\" TEXT NOT NULL );";
		final ConnectionPool cp = ConnectionPool.getSingleton();
		executeUpdate(sql, cp);
		sql = queryCreateGruppo();
		executeUpdate(sql, cp);
		sql = "CREATE TABLE \"lookAndFeel\" (\"idLook\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , \"nome\" TEXT NOT NULL , \"valore\" TEXT NOT NULL , \"usato\" INTEGER NOT NULL );";
		executeUpdate(sql, cp);
		sql = "CREATE TABLE \"risparmio\" (\"idRisparmio\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , \"PerSulTotale\" DOUBLE NOT NULL , \"nomeOggetto\" TEXT, \"costoOggetto\" DOUBLE);";
		executeUpdate(sql, cp);
		sql = "CREATE TABLE \"cat_spese\" (\"idCategoria\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\"descrizione\"  TEXT NOT NULL,\"importanza\"  TEXT NOT NULL,\"nome\"  TEXT NOT NULL,\"idGruppo\" INTEGER NOT NULL,CONSTRAINT \"keygruppo\" FOREIGN KEY (\"idGruppo\") REFERENCES \"gruppi\" (\"idGruppo\"));";
		executeUpdate(sql, cp);
		sql = "CREATE TABLE \"budget\" (\"idBudget\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\"idCategorie\"  INTEGER NOT NULL UNIQUE,\"percSulTot\"  DOUBLE NOT NULL,CONSTRAINT \"keyspesa\" FOREIGN KEY (\"idCategorie\") REFERENCES \"cat_spese\" (\"idCategoria\"));";
		executeUpdate(sql, cp);
		sql = queryCreateEntrate();
		executeUpdate(sql, cp);
		sql = queryCreateUscite();
		executeUpdate(sql, cp);
		sql = queryCreateNote();
		executeUpdate(sql, cp);

	}

	private String queryCreateEntrate() {
		StringBuilder sb = new StringBuilder();
		sb.append(" CREATE TABLE \"entrate\" ( ");
		sb.append(" \"idEntrate\"	INTEGER NOT NULL, ");
		sb.append(" \"descrizione\"	TEXT NOT NULL, ");
		sb.append(" \"Fisse_o_Var\"	TEXT NOT NULL, ");
		sb.append(" \"inEuro\"	INTEGER NOT NULL, ");
		sb.append(" \"data\"	TEXT NOT NULL, ");
		sb.append(NOME_TEXT_NOT_NULL);
		sb.append(ID_UTENTE_INTEGER_NOT_NULL);
		sb.append(" \"dataIns\"	TEXT, ");
		sb.append(" PRIMARY KEY(\"idEntrate\"), ");
		sb.append(FOREIGN_KEY_ID_UTENTE_REFERENCES_UTENTI_ID_UTENTE);
		sb.append(" ); ");
		return sb.toString();
	}

	private String queryCreateUscite() {
		StringBuilder sb = new StringBuilder();
		sb.append(" CREATE TABLE \"single_spesa\" ( ");
		sb.append(" \"idSpesa\"	INTEGER NOT NULL,  ");
		sb.append(" \"Data\"	TEXT NOT NULL, ");
		sb.append(" \"inEuro\"	INTEGER NOT NULL, ");
		sb.append(" \"descrizione\"	TEXT NOT NULL, ");
		sb.append(" \"idCategorie\"	INTEGER NOT NULL, ");
		sb.append(NOME_TEXT_NOT_NULL);
		sb.append(ID_UTENTE_INTEGER_NOT_NULL);
		sb.append(" \"dataIns\"	TEXT, ");
		sb.append(" PRIMARY KEY(\"idSpesa\") ");
		sb.append(FOREIGN_KEY_ID_UTENTE_REFERENCES_UTENTI_ID_UTENTE);
		sb.append(" ); ");
		return sb.toString();
	}

	private String queryCreateGruppo() {
		StringBuilder sb = new StringBuilder();
		sb.append(" CREATE TABLE \"gruppi\" ( ");
		sb.append(" \"idGruppo\"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,  ");
		sb.append(NOME_TEXT_NOT_NULL);
		sb.append(" \"descrizione\"	TEXT, ");
		sb.append(ID_UTENTE_INTEGER_NOT_NULL);
		sb.append(FOREIGN_KEY_ID_UTENTE_REFERENCES_UTENTI_ID_UTENTE);
		sb.append(" ); ");
		return sb.toString();
	}
	private String queryCreateNote() {
		StringBuilder sb = new StringBuilder();
		sb.append(" CREATE TABLE \"note\" ( ");
		sb.append(" \"idNote\"	INTEGER NOT NULL, ");
		sb.append(" \"nome\"	TEXT NOT NULL, ");
		sb.append(" \"descrizione\"	TEXT NOT NULL, ");
		sb.append(ID_UTENTE_INTEGER_NOT_NULL);
		sb.append(" \"data\"	TEXT NOT NULL, ");
		sb.append(" \"dataIns\"	TEXT NOT NULL, ");
		sb.append(" PRIMARY KEY(\"idNote\"), ");
		sb.append(FOREIGN_KEY_ID_UTENTE_REFERENCES_UTENTI_ID_UTENTE);
		sb.append(" ); ");
		return sb.toString();
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