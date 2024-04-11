package ex02;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*pom.xml에 jsoup 라이브러리 등록
 * ===========================================================
 * <!-- Crawling jsoup -->
		<!-- https://mvnrepository.com/artifact/org.jsoup/jsoup -->
		<dependency>
			<groupId>org.jsoup</groupId>
			<artifactId>jsoup</artifactId>
			<version>1.15.3</version>
		</dependency>
	==========================================================
 */
public class CrawlingMelonTest {
	String url="https://www.melon.com/chart/index.htm";
	public static void main(String[] args) {
		CrawlingMelonTest app = new CrawlingMelonTest();
		int size = app.crawlingMelon(app.url);
		System.out.println(size+"개의 데이터 크롤링 완료!");
	}
	private int crawlingMelon(String url2) {
		Connection con = Jsoup.connect(url2);
		try {
			Document doc=con.get();
			Elements root = doc.select("div.d_song_list");
			Elements rankEle = root.select("span.rank");
			Elements imgEle = root.select("div.wrap img");
			Elements titleEle = root.select("div.rank01 a");
			Elements singerEle = root.select("div.rank02 span");
//			System.out.println(singerEle);
			
			List<MelonVO> arr = new ArrayList<>();
			
			for(int i = 0; i<rankEle.size()-1; i++) {
				String rank = rankEle.get(i+1).text();
				String title = titleEle.get(i).text();
				String singer = singerEle.get(i).text();
				String imgUrl = imgEle.get(i).attr("src");
				MelonVO melon = new MelonVO();
				melon.setRank(rank);
				melon.setTitle(title);
				melon.setSinger(singer);
				melon.setAlbumImage(imgUrl);
				arr.add(melon);
				
//				System.out.printf("%s위 %s [%s]: %s\n", rank, title, singer, imgUrl);
			}//for----------------------
			System.out.println("arr: "+arr);
			return arr.size();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
