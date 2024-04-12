package com.multi.melon;

import java.util.List;

public interface MelonDAO {
	//멜론차트 저장
	int insertSong(List<MelonVO> melonList, String collectionName) throws Exception;
	
	//오늘 수집한 멜론 리스트 가져오기
	List<MelonVO> getSongList(String colName) throws Exception;
	
	//가수별 수집된 노래수 가져오기
	List<SumVO> getSingerSongCount(String colName) throws Exception;
	
	
	
}/////////////////////
