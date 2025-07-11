package bookSearchMashup;

import java.util.ArrayList;
import java.util.Scanner;

public class RecommendBooks {
	// フィールド
	private static String finishedBook;
	private static String genre;

	// 本をオススメするメソッド
	public void recommend() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("読み終えた本を教えてください！");
		System.out.print("本の題名：");
		finishedBook = scanner.nextLine();
		
		System.out.println();
		System.out.println(finishedBook + "を読み終わったのですね！");
		System.out.println("それはどんなジャンルの本でしたか？半角数字で教えてください！");
		System.out.println("1:ミステリー/サスペンス\n2:SF/ホラー\n3:ロマンス/ラブストーリー");
		System.out.println("4:歴史/時代小説\n5:ノンフィクション\n6:エッセイ\n7:その他\n");
		System.out.print("本のジャンル：");
		genre = scanner.nextLine();
		
		// フィードのインスタンス
		Feed feed = new Feed(finishedBook, genre, "utf-8");
		ArrayList<Item> itemList = feed.getItemList();
		System.out.println();
		System.out.println("あなたにはこちらの５冊がオススメです！\n");
		// リストのitem要素を表示
		for(Item item: itemList) {
			System.out.println(item);
			System.out.println();
		}
	}
}