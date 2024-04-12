package com.multi.melon;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
//@Document(collection ="melon")
public class MelonVO {
	@Id
	private String id;
	
	private String rank;//노래 순위
	private String title;//노래 제목
	private String singer;//가수 이름
	private String albumImage;
}
