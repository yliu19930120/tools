package com.t.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import com.mongodb.Block;

/**
 * Excel 导出遍历程序
 * 
 * @author Liu Yonghua
 * @Email liuyonghua@xinpibao.com
 * @date 2018年5月16日 下午3:28:19
 */
public class WriteExcelBlock implements Block<Document> {

	private int num;
	private List<String> fields;
	private XSSFWorkbook workbook;
	private String fielName;
	private String path;
	public WriteExcelBlock(String path,String fielName) {
		super();
		this.fielName = fielName;
		this.path = path;
	}

	@Override
	public void apply(Document t) {
           if(num == 0) {
        	   setFieldSort(t);
       		    buildBook();
           }else {
        	   addCell(t);
           }
           write(t);
           num++;
	}

	private void setFieldSort(Document t) {
		fields = t.keySet().stream().sorted().collect(Collectors.toList());
	}

	private void addCell(Document t) {
		t.keySet().stream().sorted().collect(Collectors.toList()).forEach(f->{
			if(!fields.contains(f)) {
				workbook.getSheet(fielName).getRow(0).createCell(fields.size()).setCellValue(f);
				fields.add(f);
			}
		});
	}
	//构建Book
	private void buildBook() {
		workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(fielName);
		Row rowHead = sheet.createRow(0);
		// 创建行头
		for (int i = 0; i < fields.size(); i++) {
			rowHead.createCell(i).setCellValue(fields.get(i));
		}
	}
    //写入数据
	private void write(Document t) {
		Sheet sheet = workbook.getSheet(fielName);
		Row row = sheet.createRow(num + 1);
		for (int i = 0; i < fields.size(); i++) {
			Object value = t.get(fields.get(i));
			if (value == null) {
				row.createCell(i).setCellValue("");
			} else if(value instanceof Date) {
				row.createCell(i).setCellValue((Date)value);
			}else if(value instanceof Double){
				row.createCell(i).setCellValue((Double)value);
			}else if(value instanceof Integer){
				row.createCell(i).setCellValue((Integer)value);
			}else if(value instanceof String){
				row.createCell(i).setCellValue((String)value);
			}else {
				row.createCell(i).setCellValue(value.toString());
			}
		}
	}
    //存入到文件
	public void save() {
        String fullName = path+"\\" + fielName + ".xlsx";
		File file = new File(fullName);
		try {
			file.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();
			System.out.println(fullName+"---文件写入完成\n写入记录数:"+num);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
