package accesoDatos;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import auxiliares.LeeProperties;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJDBC implements I_Acceso_Datos {

	private String driver, urlbd, user, password; // Datos de la conexion
	private Connection conn1;

	public AccesoJDBC() {
		System.out.println("ACCESO A DATOS - Acceso JDBC");

		try {
			HashMap<String, String> datosConexion;

			LeeProperties properties = new LeeProperties("Ficheros/config/accesoJDBC.properties");
			datosConexion = properties.getHash();

			driver = datosConexion.get("driver");
			urlbd = datosConexion.get("urlbd");
			user = datosConexion.get("user");
			password = datosConexion.get("password");
			conn1 = null;

			Class.forName(driver);
			conn1 = DriverManager.getConnection(urlbd, user, password);
			if (conn1 != null) {
				System.out.println("Conectado a la base de datos");
			}

		} catch (ClassNotFoundException e1) {
			System.out.println("ERROR: No Conectado a la base de datos. No se ha encontrado el driver de conexion");
			// e1.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("ERROR: No se ha podido conectar con la base de datos");
			System.out.println(e.getMessage());
			// e.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		}
	}

	public int cerrarConexion() {
		try {
			conn1.close();
			System.out.println("Cerrada conexion");
			return 0;
		} catch (Exception e) {
			System.out.println("ERROR: No se ha cerrado corretamente");
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HashMap<Integer, Deposito> depositoCompleto = new HashMap<Integer, Deposito>();

		String query = "select * from depositos";

		try {
			Statement s = conn1.createStatement();
			ResultSet rs = s.executeQuery(query);

			while (rs.next()) {

				Deposito nombreDeposito = new Deposito(rs.getString("nombre"), rs.getInt("valor"),
						rs.getInt("cantidad"));

				depositoCompleto.put(rs.getInt("valor"), nombreDeposito);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		return depositoCompleto;

	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {

		HashMap<String, Dispensador> allDispensadores = new HashMap<String, Dispensador>();

		String query = "select * from dispensadores";

		try {
			Statement s = conn1.createStatement();
			ResultSet rs = s.executeQuery(query);

			while (rs.next()) {

				Dispensador dispensador = new Dispensador(rs.getString("clave"), rs.getString("nombre"),
						rs.getInt("precio"), rs.getInt("cantidad"));

				allDispensadores.put(rs.getString("clave"), dispensador);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		return allDispensadores;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;

		String query = "UPDATE depositos SET cantidad = '?' WHERE valor = ?";
		PreparedStatement ps;

		try {
			ps = conn1.prepareStatement(query);

			for (Map.Entry<Integer, Deposito> entry : depositos.entrySet()) {

				try {
					Deposito valueElemento = entry.getValue();

					int cantidad = valueElemento.getCantidad();
					int valor = valueElemento.getValor();

					ps.setInt(1, cantidad);
					ps.setInt(2, valor);

					ps.executeUpdate();

				} catch (SQLException e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = true;

		String query = "UPDATE dispensadores SET cantidad = '?' WHERE clave = '?'";
		PreparedStatement ps;

		try {
			ps = conn1.prepareStatement(query);

			for (Map.Entry<String, Dispensador> entry : dispensadores.entrySet()) {

				try {
					Dispensador valueElemento = entry.getValue();

					int cantidad = valueElemento.getCantidad();
					String clave = valueElemento.getClave();

					ps.setInt(1, cantidad);
					ps.setString(2, clave);

					ps.executeUpdate();

				} catch (SQLException e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		return todoOK;
	}

} // Fin de la clase