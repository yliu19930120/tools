package src.test.java.com.stock;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.t.tool.TableExportTool;
import com.t.util.MongoUtil;


/**
 * 
* @author Liu Yonghua
* @date 2018年6月6日  下午2:10:17
 */
public class FactorExport {
	@Test
	public void testMethod() {
		checkMsg();
	}

	private void factoExport() {
		MongoCollection<Document> collection = MongoUtil.getCollection("datacenter", "Factor");
		TableExportTool tool = new TableExportTool(collection);
		Bson filter = new Document("typeCode","QMJ").append("frequency", 2);
//		tool.setFilter(filter);
		String path = "D:\\yliu\\temporary\\factor";
		String fileName = "test";
		tool.exportGroupByField(path, fileName, "typeCode");
	}
	
	
	private void checkMsg() {
		MongoCollection<Document> collection = MongoUtil.getCollection("datacenter", "Factor");
		List<Document> list = new ArrayList<>();
		Block<Document> block = new Block<Document>() {
			@Override
			public void apply(Document t) {
				list.add(t);
			}
		};
		collection.find().forEach(block);
	}
}
