/**
 * 
 */
package accesoDatos;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/**
 * @author Iker Solozabal IkerSolozabal4@gmail.com
 */

public class Hibernate_Solozabal implements I_Acceso_Datos {

	private static Scanner sc = new Scanner(System.in);
	private Session session;
	private static HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();
	private static HashMap<String, Dispensador> dispensadoresCreados = new HashMap<String, Dispensador>();

	public Hibernate_Solozabal() {
		// TODO Auto-generated constructor stub
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
	}

	public void createSession() {

		Configuration config = new Configuration().configure();
		SessionFactory sf = config.buildSessionFactory();
		session = sf.openSession();

	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		createSession();

		Query query = session.createQuery("SELECT d FROM Deposito d");

		List<Deposito> depositos = query.list();

		for (Deposito dep : depositos) {
			depositosCreados.put(dep.getValor(), dep);
		}

		return depositosCreados;

	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {

		createSession();

		Query query = session.createQuery("SELECT d FROM Dispensador d");

		List<Dispensador> dispensadores = query.list();

		for (Dispensador dis : dispensadores) {
			dispensadoresCreados.put(dis.getClave(), dis);
		}

		return dispensadoresCreados;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {

		boolean correcto = true;

		createSession();

		try {

			session.beginTransaction();

			for (Map.Entry<Integer, Deposito> entry : depositos.entrySet()) {
				Deposito valueDep = entry.getValue();

				session.update(valueDep);

			}

			correcto = true;

		} catch (Exception e) {
			correcto = false;
		}

		session.getTransaction().commit();
		session.close();

		return correcto;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		boolean correcto = true;

		createSession();

		try {

			session.beginTransaction();

			for (Map.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
				Dispensador valueDis = entry.getValue();

				session.update(valueDis);

			}

			correcto = true;

		} catch (Exception e) {
			correcto = false;
		}

		session.getTransaction().commit();
		session.close();

		return correcto;
	}

	/**
	 * @return the sc
	 */
	public static Scanner getSc() {
		return sc;
	}

	/**
	 * @param sc the sc to set
	 */
	public static void setSc(Scanner sc) {
		Hibernate_Solozabal.sc = sc;
	}

	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * @return the depositosCreados
	 */
	public static HashMap<Integer, Deposito> getDepositosCreados() {
		return depositosCreados;
	}

	/**
	 * @param depositosCreados the depositosCreados to set
	 */
	public static void setDepositosCreados(HashMap<Integer, Deposito> depositosCreados) {
		Hibernate_Solozabal.depositosCreados = depositosCreados;
	}

	/**
	 * @return the dispensadoresCreados
	 */
	public static HashMap<String, Dispensador> getDispensadoresCreados() {
		return dispensadoresCreados;
	}

	/**
	 * @param dispensadoresCreados the dispensadoresCreados to set
	 */
	public static void setDispensadoresCreados(HashMap<String, Dispensador> dispensadoresCreados) {
		Hibernate_Solozabal.dispensadoresCreados = dispensadoresCreados;
	}

}
