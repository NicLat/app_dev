package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Test01 {

	public static void main(String[] args) {
		
//		System.out.println("Repostory: " + args[1]);
//		System.out.println("Owner: " + args[0]);
//		System.out.println("=========");

		URL url = null;
		try {
			url = new URL("https://api.github.com/repos/" + args[0] + "/" + args[1] + "/commits");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String json = "";
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
			for (String line; (line = reader.readLine()) != null;) {
				json += line += '\n';
			}
		} catch (IOException e1) {
			System.out.println("Error getting the informations required");
			return;
			//e1.printStackTrace();
		}

		JSONParser parser = new JSONParser();

		Object input = null;
		try {
			input = parser.parse(json);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}

		JSONArray arr = (JSONArray) input;

		JSONObject last = new JSONObject();
		last.put("Last commit", (JSONObject) ((JSONObject) ((JSONObject) arr.get(0)).get("commit")).get("author") );

		JSONObject thirdtolast = new JSONObject();
		thirdtolast.put("3rd to last commit", (JSONObject) ((JSONObject) ((JSONObject) arr.get(2)).get("commit")).get("author") );

		
		JSONObject fin = new JSONObject();
		
		JSONArray items = new JSONArray();
		items.add(last);
		items.add(thirdtolast);
		fin.put("items",items);
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
		try {
			System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(fin));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
