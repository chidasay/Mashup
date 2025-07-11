package bookSearchMashup;

public class Item {
	// フィールド
	private String title;     // タイトル
	private String author;    // 著者
	private String price;     // 値段 
	
	// コンストラクタ
	public Item(String itemname, String price) {
		String item = itemname.replaceAll("【電子書籍】","");
		title = item.replaceAll("\\[[^\\]]*\\]", "");
		author = item.replace(title, "").replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", "");
		this.price = price;
	}
	
	// getメソッド
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return title;
	}
	public String getPrice() {
		return title;
	}
	
	// toStringメソッド
	@Override
	public String toString() {
		return "題名：" + title + "\n著者：" + author + "\n値段：" + price + "円";
	}
}