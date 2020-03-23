package accesoDatos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class FicherosTexto implements I_Acceso_Datos {
	
	File fDis; // FicheroDispensadores
	File fDep; // FicheroDepositos

	public FicherosTexto(){
		System.out.println("ACCESO A DATOS - FICHEROS DE TEXTO");
	}
	
	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		
		HashMap<Integer, Deposito> depositosCreados = null;
		
		return depositosCreados;
	}
	

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		
		HashMap<String, Dispensador> dispensadoresCreados = null;

		return dispensadoresCreados;
		
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {

		boolean todoOK = true;


		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		boolean todoOK = true;


		return todoOK;
	}

} // Fin de la clase