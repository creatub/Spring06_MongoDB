package com.multi.melon;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection ="melon")
public class MelonVO {
	private String id;
	private String rank;//노래 순위
	private String title;
	private String singer;
	private String albumImage;
}
