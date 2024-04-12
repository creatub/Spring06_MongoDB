package com.multi.melon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;

import lombok.RequiredArgsConstructor;

@Repository("melonDao")
@RequiredArgsConstructor
public class MelonDAOImpl implements MelonDAO {

	private final MongoTemplate mongoTemplate;
	
	@Override
	public int insertSong(List<MelonVO> melonList, String collectionName) throws Exception {
		Collection<MelonVO> col=mongoTemplate.insert(melonList, collectionName);
		return col.size();
	}

	@Override
	public List<MelonVO> getSongList(String colName) throws Exception {
		return mongoTemplate.findAll(MelonVO.class,colName);
	}//----
	
	
	/*
	db.Melon_240412.aggregate([
	    {$group:{_id:'$singer', singerCnt:{$sum:1}}},
	    {$project:{singer:'$_id',singerCnt:'$singerCnt'}},
	    {$match:{singerCnt:{$gt:1}}},
	    {$sort:{singerCnt:-1}}
	]);
	 */
	@Override
	public List<SumVO> getSingerSongCount(String colName) throws Exception {
		MongoCollection<Document> col = mongoTemplate.getCollection(colName);
		
		List<? extends Bson> pipeline = Arrays.asList(
				new Document().append("$group", 
						new Document().append("_id", "$singer").append("singerCnt", new Document().append("$sum", 1))),
				new Document().append("$project", new Document().append("singer", "$_id").append("singerCnt", "$singerCnt")),
				new Document().append("$match", new Document().append("singerCnt", new Document().append("$gt", 1))),
				new Document().append("$sort", new Document().append("singerCnt", -1))
				);
		AggregateIterable<Document> cr = col.aggregate(pipeline);
		List<SumVO> arr = new ArrayList<>();
		for(Document doc:cr) {
			if(doc==null) {
				doc=new Document();
			}
			String singer=doc.getString("singer");
			int singerCnt=doc.getInteger("singerCnt",0);//디폴트값:0
			SumVO vo = new SumVO();
			vo.setSinger(singer);
			vo.setSingerCnt(singerCnt);
			
			arr.add(vo);
		}//for--------------
		return arr;
	}

}
