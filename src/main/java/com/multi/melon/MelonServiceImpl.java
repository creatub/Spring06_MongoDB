package com.multi.melon;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service("melonService")
@RequiredArgsConstructor
public class MelonServiceImpl implements MelonService {

	private final MelonDAO mDao;
	private String url = "https://www.melon.com/chart/index.htm";
	@Override
	public int collectMelonList() throws Exception {
		//멜론차트 크롤링
		List<MelonVO> arr = this.crawlingMelon(url);
		//크롤링한 데이터 몽고디비에 저장
//		String str = new SimpleDateFormat("yyMMdd").format(new Date());
//		//컬렉션명: Melon_240412
		String collectionName=this.getCollectionName();
		
		return mDao.insertSong(arr, collectionName);
	}//--------------------------
	private String getCollectionName() {
		String str = new SimpleDateFormat("yyMMdd").format(new Date());
		//컬렉션명: Melon_240412
		String collectionName="Melon_"+str;
		return collectionName;
	}//---------------------------
	
	private List<MelonVO> crawlingMelon(String url2) {
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
			return arr;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MelonVO> getSongList() throws Exception {
		return mDao.getSongList(this.getCollectionName());
	}
	
	@Override
	public List<SumVO> getSingerSongCount() throws Exception{
		return mDao.getSingerSongCount(this.getCollectionName());
	}

}
