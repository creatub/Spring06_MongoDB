package com.multi.posts;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/*
db.sequence.insertOne({collectionName:'posts', count:0})
db.sequence.find()
 */
@Data
@Document(collection="sequence")
public class SeqVO {

	@Id
	private String id;
	
	@BsonProperty
	private String collectionName;
	
	@BsonProperty
	private int count;
}
