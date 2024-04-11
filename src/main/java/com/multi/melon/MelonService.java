package com.multi.melon;

import java.util.List;


public interface MelonService {
	//멜론 차트 노래 리스트 저장
	int collectMelonList() throws Exception;
	
	//오늘의 멜론 노래 리스트
	List<MelonVO> getSongList() throws Exception;
	
	//가수별 노래수 가져오기
	
}
