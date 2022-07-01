package com.c1se44.school_connect.Exporter;

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

public class UserExcelExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<userEntity> listUsers;
	
	public UserExcelExporter(List<userEntity> listUsers) {
		this.listUsers = listUsers;
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
		
		createCell(row, 0, "Code", style);
		createCell(row, 1, "Username", style);
		createCell(row, 2, "E-mail", style);
		createCell(row, 3, "Full Name", style);
		createCell(row, 4, "gender", style);
		createCell(row, 5, "Date Of Birth", style);
		createCell(row, 6, "Phone Number", style);
		createCell(row, 7, "Address of user", style);
		createCell(row, 8, "Position", style);
		createCell(row, 9, "Enabled", style);
		createCell(row, 10, "Created By", style);
		createCell(row, 11, "Created Date", style);
		
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
		
		for (userEntity user : listUsers) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			createCell(row, columnCount++, user.getCode(), style);
			createCell(row, columnCount++, user.getUsername(), style);
			createCell(row, columnCount++, user.getEmail(), style);
			createCell(row, columnCount++, user.getFullName(), style);
			createCell(row, columnCount++, user.getGender(), style);
			createCell(row, columnCount++, user.getDateOfBirth().toString(), style);
			createCell(row, columnCount++, user.getNumberPhone(), style);
			createCell(row, columnCount++, user.getAddressOfUser(), style);
			createCell(row, columnCount++, user.getPosition(), style);
			createCell(row, columnCount++, user.getStatus().toString(), style);
			createCell(row, columnCount++, user.getCreatedby(), style);
			createCell(row, columnCount++, user.getCreateDate().toLocalDate().toString(), style);
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