package com.t.tool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.t.util.DateUtil;

/**
 * 
* @author Liu Yonghua
* @date 2018年6月1日  下午4:57:44
 */
public class TableExportTool {

	private MongoCollection<Document> collection;

	private Bson filter;
	
	private Bson sort;
	
	private Bson projection;
	
	public TableExportTool(MongoCollection<Document> collection) {
		super();
		this.collection = collection;
	}
	
	public void export(String path,String fileName) {
		WriteExcelBlock block = new WriteExcelBlock(path, fileName);
		checkNull();
		collection.find(filter).projection(projection).sort(sort).forEach(block);
		block.save();
	}
	
	//分字段导出
	public void exportGroupByField(String path,String fileName,String fieldName) {
		Map<Object,WriteExcelBlock> blockMap = new HashMap<>();
		Block<Document> block = new  Block<Document>() {
			@Override
			public void apply(Document t) {
				Object key = t.get(fieldName);
				if(!blockMap.containsKey(key)) {
					blockMap.put(key, new WriteExcelBlock(path, getKeyStr(key)));
				}
				blockMap.get(key).apply(t);
			}
		};
		checkNull();
		collection.find(filter).projection(projection).sort(sort).forEach(block);
		blockMap.values().forEach(v->v.save());
	}
	private String getKeyStr(Object key) {
		String keyStr = "";
		if(key instanceof Date) {
			keyStr = DateUtil.dateToStr((Date)key, "yyyyMMdd");
		}else {
			keyStr = key.toString();
		}
		return keyStr;
	}
	private void checkNull() {
		if(filter==null) filter = new Document();
		if(projection==null) projection = new Document();
		if(sort==null)sort = new Document("_id",1);
	}
	public MongoCollection<Document> getCollection() {
		return collection;
	}

	public void setCollection(MongoCollection<Document> collection) {
		this.collection = collection;
	}

	public Bson getFilter() {
		return filter;
	}

	public void setFilter(Bson filter) {
		this.filter = filter;
	}

	public Bson getSort() {
		return sort;
	}

	public void setSort(Bson sort) {
		this.sort = sort;
	}

	public Bson getProjection() {
		return projection;
	}

	public void setProjection(Bson projection) {
		this.projection = projection;
	}
	
	
}
