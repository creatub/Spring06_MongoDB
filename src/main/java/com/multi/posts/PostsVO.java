package com.multi.posts;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="posts")
public class PostsVO {

	@Id
	private String id;
	
	@BsonProperty(value="no")
	private int no;//글번호
	
	@BsonProperty(value="author")//option이지만 붙여줌
	private String author;
	@BsonProperty(value="title")
	private String title;
	@BsonProperty(value="wdate")
	private String wdate;
}
