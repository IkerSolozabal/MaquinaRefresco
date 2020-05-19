package auxiliares;

import com.squareup.okhttp.*;

import java.io.IOException;

public class ApiRequests {
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	OkHttpClient client;

	public ApiRequests() {
		client = new OkHttpClient();
	}

	public String depositoRequest(String url, String json, String metodo) {
		try {
			if (metodo.equalsIgnoreCase("GET")) {

				Request request = new Request.Builder().url(url).get().build();
				Response response = client.newCall(request).execute();

				return response.body().string();
			}

			if (metodo.equalsIgnoreCase("PUT")) {
				RequestBody body = RequestBody.create(JSON, json);
				Request request = new Request.Builder().url(url).put(body).build();
				Response response = client.newCall(request).execute();

				return response.body().string();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;

	}

	public String dispensadorRequest(String url, String json, String metodo) {
		try {
			if (metodo.equalsIgnoreCase("GET")) {

				Request request = new Request.Builder().url(url).get().build();
				Response response = client.newCall(request).execute();

				return response.body().string();
			}

			if (metodo.equalsIgnoreCase("PUT")) {
				RequestBody body = RequestBody.create(JSON, json);
				Request request = new Request.Builder().url(url).put(body).build();
				Response response = client.newCall(request).execute();

				return response.body().string();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

}