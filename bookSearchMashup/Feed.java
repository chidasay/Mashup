package bookSearchMashup;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;

public class Feed {
	// フィールド
	private String urlString;    // url
	private Document document;   // XML文書のDOMツリー
	
	private String finishedBook; // 読み終えた本の名前
	
	// コンストラクタ
	public Feed(String title, String genre, String encoding) {
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		
		this.finishedBook = title;
		
		switch(genre) {
			case "1": // ミステリー/サスペンス
				urlString = "https://ranking.rakuten.co.jp/rss/daily/208620/";
				break;
			case "2": // SF/ホラー
				urlString = "https://ranking.rakuten.co.jp/rss/daily/208625/";
				break;
			case "3": // ロマンス/ラブストーリー
				urlString = "https://ranking.rakuten.co.jp/rss/daily/208640/";
				break;
			case "4": // 歴史/時代小説
				urlString = "https://ranking.rakuten.co.jp/rss/daily/200491/";
				break;
			case "5": // ノンフィクション
				urlString = "https://ranking.rakuten.co.jp/rss/daily/208635/";
				break;
			case "6": // エッセイ
				urlString = "https://ranking.rakuten.co.jp/rss/daily/208630/";
				break;
			case "7": // その他
				urlString = "https://ranking.rakuten.co.jp/rss/daily/101930/";
				break;
			default:
				System.out.println("番号が間違っているため、その他として検索します。");
				urlString = "https://ranking.rakuten.co.jp/rss/daily/101930/";
				break;
		}
		
		try {
			// InputStream
			URL url = new URI(urlString).toURL();
			URLConnection connection = url.openConnection();
			connection.connect();
			InputStream inputStream = connection.getInputStream();
			// DOMツリー
			document = this.buildDocument(inputStream, encoding);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// item要素の ArrayList
	public ArrayList<Item> getItemList() {
		ArrayList<Item> itemList = new ArrayList<Item>();
		try {
			// XPath の表現を扱う XPath オブジェクトを生成
			XPath xPath = XPathFactory.newInstance().newXPath();
			// item要素のリストを得る (RSS 2.0)
			NodeList itemNodeList = (NodeList)xPath.evaluate("/rss/channel/item", document, XPathConstants.NODESET);
			
			// Item（もし読み終わった本と被っていたら、含めず次に）
			int j = 0;
			for(int i = 0; i < 5; i++) {
				Node itemNode= itemNodeList.item(j);
				String itemname = xPath.evaluate("itemname", itemNode);
				String price = xPath.evaluate("price", itemNode);
				
				String title = itemname.replaceAll("【電子書籍】","").replaceAll("\\[[^\\]]*\\]", "");
				if(title.contains(finishedBook)) i--;
				else itemList.add(new Item(itemname, price)); j++;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return itemList;
	}
	
	//DOM ツリー
	public Document buildDocument(InputStream inputStream, String encoding) {
		Document document = null;
		try {
			DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
			DOMImplementationLS implementation = (DOMImplementationLS)registry.getDOMImplementation("XML 1.0");
			
			LSInput input = implementation.createLSInput();
			input.setByteStream(inputStream);
			input.setEncoding(encoding);
			LSParser parser = implementation.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
			parser.getDomConfig().setParameter("namespaces", false);
			// DOMツリーの構築
			document = parser.parse(input);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return document; // 文書全体のDOMツリー
	}
}