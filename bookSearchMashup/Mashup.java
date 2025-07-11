package bookSearchMashup;

public class Mashup {
	public static void main(String[] args) {
		RecommendBooks rec = new RecommendBooks();
		rec.recommend();
		
		Overpass pass = new Overpass();
		pass.find();
		
		System.out.println("\nご利用ありがとうございました！");
		System.out.println("新しい本を片手に良い時間をお過ごしください！");
	}
}