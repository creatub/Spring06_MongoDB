package ex01;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TestMongodb {

	public static void main(String[] args) {
		String db="mydb";//데이터베이스
		String table="posts";//컬렉션명
		
		MongoClient mongo_client=null;
		MongoDatabase mongodb=null;
		MongoCollection<Document>  collection =null;
		
		//몽고디비 연결
		mongo_client = MongoClients.create("mongodb://localhost:27017");
		System.out.println("mongo_client: "+mongo_client);
		
		mongodb=mongo_client.getDatabase(db);//user mydb와 동일
		
		collection = mongodb.getCollection(table);
		System.out.println(table+" 컬렉션 생성 성공!!");
		
		Document doc=new Document();
		doc.append("author", "김혜성");
		doc.append("title", "몽고디비 테스트 중")
			.append("wdate","2024-04-09");
		
		collection.insertOne(doc);
		System.out.println("posts 컬렉션에 도큐먼트 삽입 성공!!");
		
		mongo_client.close();

	}

}
