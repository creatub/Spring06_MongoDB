package com.multi.melon;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
//@Document(collection ="melon")
public class MelonVO {
	@Id
	private String id;
	
	private String rank;//�뷡 ����
	private String title;//�뷡 ����
	private String singer;//���� �̸�
	private String albumImage;
}
