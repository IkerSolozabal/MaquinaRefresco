package accesoDatos;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class Fichero_Solozabal implements I_Acceso_Datos {

	File fDis = new File("Ficheros\\datos\\dispensadores.txt"); // FicheroDispensadores
	File fDep = new File("Ficheros\\datos\\depositos.txt"); // FicheroDepositos

	public Fichero_Solozabal() {
		System.out.println("ACCESO A DATOS - FICHEROS DE TEXTO");
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();

		try {
			FileReader fr = new FileReader(fDep);
			BufferedReader bf = new BufferedReader(fr);
			String fichero;

			while ((fichero = bf.readLine()) != null) {
				String[] ficheroSplittedArray = fichero.split(";");
				String nombre = ficheroSplittedArray[0];
				int valor = Integer.parseInt(ficheroSplittedArray[1]);
				int cantidad = Integer.parseInt(ficheroSplittedArray[2]);

				depositosCreados.put(valor, new Deposito(nombre, valor, cantidad));
			}

			bf.close();

		} catch (Exception e) {
			System.err.println("obtenerDepositos() FicherosTexto");
			e.printStackTrace();
			System.exit(0);

		}

		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {

		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<String, Dispensador>();

		try {
			FileReader fr = new FileReader(fDis);
			BufferedReader bf = new BufferedReader(fr);
			String fichero;

			while ((fichero = bf.readLine()) != null) {
				String[] ficheroSplittedArray = fichero.split(";");
				String clave = ficheroSplittedArray[0];
				String nombre = ficheroSplittedArray[1];
				int precio = Integer.parseInt(ficheroSplittedArray[2]);
				int cantidad = Integer.parseInt(ficheroSplittedArray[3]);

				dispensadoresCreados.put(clave, new Dispensador(clave, nombre, precio, cantidad));
			}

			bf.close();

		} catch (Exception e) {
			System.err.println("obtenerDispensadores() FicherosTexto");
			e.printStackTrace();
			System.exit(0);
		}

		return dispensadoresCreados;

	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {

		boolean todoOK = true;

		try {

			FileWriter w = new FileWriter(fDep);
			BufferedWriter bw = new BufferedWriter(w);
			PrintWriter wr = new PrintWriter(bw);

			for (Map.Entry<Integer, Deposito> entry : depositos.entrySet()) {

				Deposito valueDeposito = entry.getValue();

				String nombreMoneda = valueDeposito.getNombreMoneda();
				int valorMoneda = valueDeposito.getValor();
				int cantidadMoneda = valueDeposito.getCantidad();

				wr.write(nombreMoneda + ";" + valorMoneda + ";" + cantidadMoneda + "\n");

			}

			wr.close();

			bw.close();

		} catch (IOException e) {
			System.err.println("guardarDepositos() FicherosTexto");
			e.printStackTrace();
			todoOK = false;
			System.exit(0);
		}

		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		boolean todoOK = true;

		try {

			FileWriter w = new FileWriter(fDis);
			BufferedWriter bw = new BufferedWriter(w);
			PrintWriter wr = new PrintWriter(bw);

			for (Map.Entry<String, Dispensador> entry : dispensadores.entrySet()) {

				Dispensador valueDispensador = entry.getValue();

				String clave = valueDispensador.getClave();
				String nombreCompleto = valueDispensador.getNombreProducto();
				int precio = valueDispensador.getPrecio();
				int cantidad = valueDispensador.getCantidad();

				wr.write(clave + ";" + nombreCompleto + ";" + precio + ";" + cantidad + "\n");

			}

			wr.close();

			bw.close();

		} catch (IOException e) {
			System.err.println("guardarDepositos() FicherosTexto");
			e.printStackTrace();
			todoOK = false;
			System.exit(0);
		}

		return todoOK;
	}

} // Fin de la clase