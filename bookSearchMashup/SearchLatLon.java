package bookSearchMashup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SearchLatLon {
	// フィールド
	private double[] latLon = new double[2];  // 経度と緯度を入れる配列
	
	// 場所をStringで受け取って緯度と経度を返す
	public double[] search() {
		// TLSを一応やっておく
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		
		System.out.println("それでは本を買いに行きましょう！");
		System.out.print("現在地を教えてください。地名だと精度が飛躍的に向上します：");
		
		// 場所を入力してもらう
		Scanner scanner = new Scanner(System.in);
		String place = scanner.nextLine();
		
		try {
			// APIのURL
			String urlString = "https://nominatim.openstreetmap.org/search" + "?q=" + 
					URLEncoder.encode(place, "UTF-8") + "&format=json&limit=1";
			// URLオブジェクトを作成
			URL url = new URL(urlString);
			
			// HTTPリクエスト
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");
			
			if (connection.getResponseCode() == 200) {
				// 読み取り
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder response = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();
				
				// JSON解析
				ObjectMapper mapper = new ObjectMapper();
				JsonNode root = mapper.readTree(response.toString());
				
				JsonNode location = root.get(0);
				
				if(location != null) {
					latLon[0] = location.get("lat").asDouble();
					latLon[1] = location.get("lon").asDouble();

				} else {
					System.out.println("場所の入力をもう少し工夫して、初めからやり直してくださいね。");
					System.exit(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return latLon;
	}
}