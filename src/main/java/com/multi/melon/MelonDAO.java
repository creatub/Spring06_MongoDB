package com.multi.melon;

import java.util.List;

public interface MelonDAO {
	//�����Ʈ ����
	int insertSong(List<MelonVO> melonList, String collectionName) throws Exception;
	
	//���� ������ ��� ����Ʈ ��������
	List<MelonVO> getSongList(String colName) throws Exception;
	
	//������ ������ �뷡�� ��������
	List<SumVO> getSingerSongCount(String colName) throws Exception;
	
	
	
}/////////////////////
