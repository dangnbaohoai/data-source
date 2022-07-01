package com.c1se44.school_connect.Exporter;

import com.c1se44.school_connect.DTO.Response.ForumResponse;
import com.c1se44.school_connect.entity.userEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ForumExcelExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<ForumResponse> listForum;
	
	public ForumExcelExporter(List<ForumResponse> listForum) {
		this.listForum = listForum;
		workbook = new XSSFWorkbook();
	}
	
	
	private void writeHeaderLine() {
		sheet = workbook.createSheet("Users");
		
		Row row = sheet.createRow(0);
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		createCell(row, 0, "Forum ID", style);
		createCell(row, 1, "Forum name", style);
		createCell(row, 2, "Censor Id", style);
		createCell(row, 3, "Censor Name", style);
		createCell(row, 4, "Description", style);
		createCell(row, 5, "Number Members", style);
		createCell(row, 6, "Number Post", style);
		createCell(row, 7, "Number Members not censor", style);
		createCell(row, 8, "Number Post not censor", style);
		createCell(row, 9, "Number report of post current", style);
		createCell(row, 10, "Number report of comment current", style);
		createCell(row, 11, "Created By", style);
		createCell(row, 12, "Created Date", style);
		createCell(row, 13, "Enabled", style);
		
	}
	
	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		}else if(value instanceof Long)
			cell.setCellValue((Long) value);
		else{
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}
	
	private void writeDataLines() {
		int rowCount = 1;
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		
		for (ForumResponse forum : listForum) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			
			createCell(row, columnCount++, forum.getForumId(), style);
			createCell(row, columnCount++, forum.getNameForum(), style);
			createCell(row, columnCount++, forum.getCenserId(), style);
			createCell(row, columnCount++, forum.getCenserName(), style);
			createCell(row, columnCount++, forum.getDescription(), style);
			createCell(row, columnCount++, forum.getNumberMembers(), style);
			createCell(row, columnCount++, forum.getNumberPosts(), style);
			createCell(row, columnCount++, forum.getNumberMembersNotCensor(), style);
			createCell(row, columnCount++, forum.getNumberPostsNotCensor(), style);
			createCell(row, columnCount++, forum.getNumberReportPost(), style);
			createCell(row, columnCount++, forum.getNumberReportComments(), style);
			createCell(row, columnCount++, forum.getCreateBy(), style);
			createCell(row, columnCount++, forum.getCreateDate().toLocalDate().toString(), style);
			createCell(row, columnCount++, forum.getStatus(), style);
		}
	}
	
	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLines();
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		
		outputStream.close();
		
	}
}