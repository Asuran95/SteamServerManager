package steamservermanager.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONArray;
import org.json.JSONObject;

public class SteamAPIUtils {
	
	public static String getGameNameBySteamId(Integer steamId) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.steampowered.com/ICommunityService/GetApps/v1/?appids[0]="+steamId)).build();
		
		try {
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

			return getAppNameInResponseBody(response.body());
			
		} catch (IOException e) {
			throw new RuntimeException(e);
			
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static String getAppNameInResponseBody(String responseBody) {
		JSONObject body = new JSONObject(responseBody);
		JSONObject response = body.getJSONObject("response");
		JSONArray apps = response.getJSONArray("apps");
		
		for (int i = 0 ; i < apps.length(); i++) {
			JSONObject app = apps.getJSONObject(i);	
			
			if (app.has("name")) {
				String name = app.getString("name");
				
				return name;
			}
		}
		
		return null;
	}
}
