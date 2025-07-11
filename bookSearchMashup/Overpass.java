package bookSearchMashup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Overpass {
	// フィールド
	private static final double r = 1000;       // 半径1000m
	private static final double d = 111000.0;   // 一度の距離
	
	private double[] location; // 現在地の緯度と経度
	private String[] bookstores = new String[5]; // 最大5件の本屋情報
	
	public void find() {
		// TLS設定
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		
		SearchLatLon latLon = new SearchLatLon();
		location = latLon.search();
		try {
			// 検索範囲の計算
			double south = location[0] - r / d;
			double north = location[0] + r / d;
			double west = location[1] - (r / (d * Math.cos(Math.toRadians(location[0]))));
			double east = location[1] + (r / (d * Math.cos(Math.toRadians(location[0]))));
			
			// 本屋検索クエリ
			String booksQuery = String.format("[out:json]; node[\"shop\"=\"books\"]"
					+ "(%f,%f,%f,%f); out;", south, west, north, east);
			
			// 本屋の情報を取得
			retrieve(booksQuery);
			
			// 結果表示
			result("本屋", bookstores);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void retrieve(String query) {
		int count = 0;
		
		try {
			String urlString = "https://lz4.overpass-api.de/api/interpreter?data=" + URLEncoder.encode(query, "UTF-8");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(urlString).openStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			
			// JSON解析
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.toString());
			JsonNode elements = root.get("elements");
			
			if (elements != null) {
				for (JsonNode element : elements) {
					if (count >= 5) break;
					
					String name = element.path("tags").path("name").asText();
					if (!name.isEmpty()) {
						bookstores[count++] = name;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void result(String type, String[] places) {
		if (places[0] == null) {
			System.out.println("\nすみません。近くに" + type + "はありませんでした……");
		} else {
			System.out.println("\n現在地の近くにある" + type + "はこちらです！\n");
			for (String place : places) {
				if (place != null) {
					System.out.println("・" + place);
				}
			}
		}
	}
}