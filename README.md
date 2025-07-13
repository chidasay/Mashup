# Mashup

概要  
WebAPIを用いて、おすすめの本と現在地近くの本屋を検索し、表示するプログラムです。  
外部APIの稼働状況によっては、うまく動作しない場合があります。ご了承ください。  
  
使用技術  
Java/楽天RSS/OpenStreetMap/OverpassAPI/ 
  
構成  
bookSearchMashup  
┣ Mashup          #全体の制御を行うクラス  
┣ RecommendBooks  #ユーザから本の情報を聞き取り、おすすめの本を表示するクラス  
┣ Feed            #楽天ランキングのRSSから、おすすめの本を取得するクラス  
┣ Item            #本の情報を格納するデータの構造  
┣ SearchLatLon    #OpenStreetMapのNominatimAPIを用いて、地名から緯度と経度を取得するクラス  
┣ Overpass        #現在地から最も近い本屋を、OverpassAPIを用いて表示するクラス  
┗ README.md       #説明ファイル  
  
その他は、ポートフォリオをの方をご参照ください。
