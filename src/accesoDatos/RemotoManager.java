package accesoDatos;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import auxiliares.ApiRequests;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class RemotoManager implements I_Acceso_Datos {

	private static Scanner sc = new Scanner(System.in);
	ApiRequests encargadoPeticiones;
	private String BaseUrl, dispensadoresUrl, depositosUrl; // Datos de la conexion

	public RemotoManager() {

		encargadoPeticiones = new ApiRequests();

		BaseUrl = "http://localhost/IkerSolozabal/MaquinaRefrescos/";
		dispensadoresUrl = BaseUrl + "dispensadores.php";
		depositosUrl = BaseUrl + "depositos.php";

	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> data = new HashMap<Integer, Deposito>();

		try {

			System.out.println("La url a la que lanzamos la petición es " + depositosUrl); //
			// Traza para pruebas

			String response = encargadoPeticiones.depositoRequest(depositosUrl, "Null", "GET");

//			System.out.println(response); // Traza para pruebas
//			
//			System.exit(0);

			// Parseamos la respuesta y la convertimos en un JSONObject
			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

			if (respuesta == null) { // Si hay algún error de parseo (json
										// incorrecto porque hay algún caracter
										// raro, etc.) la respuesta será null
				 System.out.println("El json recibido no es correcto. Finaliza la ejecución");
				System.exit(-1);
			} else { // El JSON recibido es correcto
				// Sera "ok" si todo ha ido bien o "error" si hay algún problema
				String estado = (String) respuesta.get("estado");
				// Si ok, obtenemos array de jugadores para recorrer y generar hashmap
				if (estado.equals("ok")) {
					JSONArray array = (JSONArray) respuesta.get("depositos");

					if (array.size() > 0) {

						// Declaramos variables
						Deposito nuevoDeposito;
						int id;
						String nombre;
						int valor;
						int cantidad;

						for (int i = 0; i < array.size(); i++) {
							JSONObject row = (JSONObject) array.get(i);

							id = Integer.parseInt((String) row.get("id"));
							nombre = (String) row.get("nombre");

							valor = Integer.parseInt((String) row.get("valor"));
							cantidad = Integer.parseInt((String) row.get("cantidad"));

							nuevoDeposito = new Deposito(nombre, valor, cantidad);
							// System.out.println(nuevoElemento.toString());
							data.put(valor, nuevoDeposito);
						}

						// System.out.println("Tamaño " + data.size());

						// System.out.println("Acceso JSON Remoto - Leidos datos correctamente y
						// generado hashmap");
						System.out.println();

					} else { // El array de jugadores está vacío
						// System.out.println("Acceso JSON Remoto - No hay datos que tratar");
						System.out.println();
					}

				} else { // Hemos recibido el json pero en el estado se nos
							// indica que ha habido algún error

					// System.out.println("Ha ocurrido un error en la busqueda de datos");
					// System.out.println("Error: " + (String) respuesta.get("error"));
					// System.out.println("Consulta: " + (String) respuesta.get("query"));

					System.exit(-1);

				}
			}

		} catch (Exception e) {

			e.printStackTrace();

			System.exit(-1);
		}

		return data;

	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> data = new HashMap<String, Dispensador>();

		try {

			System.out.println("La url a la que lanzamos la petición es " + dispensadoresUrl); //
			// Traza para pruebas

			String response = encargadoPeticiones.dispensadorRequest(dispensadoresUrl, "Null", "GET");

//			System.out.println(response); // Traza para pruebas
//			
//			System.exit(0);

			// Parseamos la respuesta y la convertimos en un JSONObject
			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

			if (respuesta == null) { // Si hay algún error de parseo (json
										// incorrecto porque hay algún caracter
										// raro, etc.) la respuesta será null
				// System.out.println("El json recibido no es correcto. Finaliza la ejecución");
				System.exit(-1);
			} else { // El JSON recibido es correcto
				// Sera "ok" si todo ha ido bien o "error" si hay algún problema
				String estado = (String) respuesta.get("estado");
				// Si ok, obtenemos array de jugadores para recorrer y generar hashmap
				if (estado.equals("ok")) {
					JSONArray array = (JSONArray) respuesta.get("dispensadores");

					if (array.size() > 0) {

						// Declaramos variables
						Dispensador nuevoDispensador;
						int id;
						String clave;
						String nombre;
						int precio;
						int cantidad;

						for (int i = 0; i < array.size(); i++) {
							JSONObject row = (JSONObject) array.get(i);

							id = Integer.parseInt((String) row.get("id"));
							clave = (String) row.get("clave");
							nombre = (String) row.get("nombre");

							precio = Integer.parseInt((String) row.get("precio"));
							cantidad = Integer.parseInt((String) row.get("cantidad"));

							nuevoDispensador = new Dispensador(clave, nombre, precio, cantidad);
							// System.out.println(nuevoElemento.toString());
							data.put(clave, nuevoDispensador);
						}

						// System.out.println("Tamaño " + data.size());

						// System.out.println("Acceso JSON Remoto - Leidos datos correctamente y
						// generado hashmap");
						System.out.println();

					} else { // El array de jugadores está vacío
						// System.out.println("Acceso JSON Remoto - No hay datos que tratar");
						System.out.println();
					}

				} else { // Hemos recibido el json pero en el estado se nos
							// indica que ha habido algún error

					// System.out.println("Ha ocurrido un error en la busqueda de datos");
					// System.out.println("Error: " + (String) respuesta.get("error"));
					// System.out.println("Consulta: " + (String) respuesta.get("query"));

					System.exit(-1);

				}
			}

		} catch (Exception e) {

			e.printStackTrace();

			System.exit(-1);
		}

		return data;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) throws IOException {

		JSONObject objDeposito = new JSONObject();
		JSONObject objPeticion = new JSONObject();

		for (Map.Entry<Integer, Deposito> entry : depositos.entrySet()) {
			boolean todoCorrecto;
			Deposito valueDeposito = entry.getValue();
			Deposito deposito = new Deposito(valueDeposito.getNombreMoneda(), valueDeposito.getValor(),
					valueDeposito.getCantidad());

			objDeposito.put("nombre", deposito.getNombreMoneda());
			objDeposito.put("valor", deposito.getValor());
			objDeposito.put("cantidad", deposito.getCantidad());

			// Tenemos el jugador como objeto JSON. Lo añadimos a una peticion
			// Lo transformamos a string y llamamos al
			// encargado de peticiones para que lo envie al PHP

			objPeticion.put("peticion", "guardar");
			objPeticion.put("depositoGuardar", objDeposito);

			String json = objPeticion.toJSONString();

			// System.out.println("Lanzamos peticion JSON para almacenar un elemento");

			String url = BaseUrl;

			// System.out.println("La url a la que lanzamos la petición es " + url);
			// System.out.println("El json que enviamos es: ");
			// System.out.println(json);
			// System.exit(-1);

			String response = encargadoPeticiones.depositoRequest(depositosUrl, json, "PUT");

			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());
			String estado = (String) respuesta.get("estado");

			if (estado.equals("ok")) {
				todoCorrecto = true;
			} else {
				todoCorrecto = false;
			}
		}

		return true;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) throws IOException {
		JSONObject objDispesador = new JSONObject();
		JSONObject objPeticion = new JSONObject();

		for (Map.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
			boolean todoCorrecto;
			Dispensador valueDispensador = entry.getValue();
			Dispensador dispensador = new Dispensador(valueDispensador.getClave(), valueDispensador.getNombreProducto(),
					valueDispensador.getPrecio(), valueDispensador.getCantidad());

			objDispesador.put("clave", dispensador.getClave());
			objDispesador.put("nombre", dispensador.getNombreProducto());
			objDispesador.put("precio", dispensador.getPrecio());
			objDispesador.put("cantidad", dispensador.getCantidad());

			// Tenemos el jugador como objeto JSON. Lo añadimos a una peticion
			// Lo transformamos a string y llamamos al
			// encargado de peticiones para que lo envie al PHP

			objPeticion.put("peticion", "guardar");
			objPeticion.put("dispensadorGuardar", objDispesador);

			String json = objPeticion.toJSONString();

			// System.out.println("Lanzamos peticion JSON para almacenar un elemento");

			// System.out.println("La url a la que lanzamos la petición es " + url);
			// System.out.println("El json que enviamos es: ");
			// System.out.println(json);
			// System.exit(-1);

			String response = encargadoPeticiones.dispensadorRequest(dispensadoresUrl, json, "PUT");
			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());
			String estado = (String) respuesta.get("estado");

			if (estado.equals("ok")) {
				todoCorrecto = true;
			} else {
				todoCorrecto = false;
			}
		}

		return true;

	}

}
