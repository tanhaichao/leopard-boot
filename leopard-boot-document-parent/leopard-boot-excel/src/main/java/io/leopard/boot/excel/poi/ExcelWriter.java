package io.leopard.boot.excel.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {

	private Workbook workbook;

	private Sheet sheet;

	public ExcelWriter() {
		this(false);
	}

	public ExcelWriter(boolean xlsx) {
		if (xlsx) {
			this.workbook = new XSSFWorkbook();
		}
		else {
			this.workbook = new HSSFWorkbook();
		}
		this.init();
	}

	public void createSheet(String sheetName) {
		this.sheet = workbook.createSheet(sheetName);
	}

	public void selectSheet(String sheetName) {
		this.sheet = this.workbook.getSheet(sheetName);
	}

	private void init() {
		{
			Font font = this.workbook.createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short) 11);
			font.setFontName("宋体");
			CellStyle style = workbook.createCellStyle();
			style.setFont(font);
			this.headerCellStyle = style;

		}
		{

			Font font = this.workbook.createFont();
			font.setBold(false);
			font.setFontHeightInPoints((short) 11);
			font.setFontName("宋体");

			CellStyle style = workbook.createCellStyle();
			style.setFont(font);
			this.mainCellStyle = style;
		}
	}

	private CellStyle headerCellStyle;

	private CellStyle mainCellStyle;

	public void addHeaderCell(String columnName) {
		Row row = this.sheet.getRow(0);
		if (row == null) {
			row = this.sheet.createRow(0);
		}
		short cellCount = row.getLastCellNum();
		if (cellCount == -1) {
			cellCount = 0;
		}

		// font.setFontHeight((short) 50);
		Cell cell = row.createCell(cellCount);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(columnName);

	}

	public void addColumnName(String... columnNames) {
		for (int i = 0; i < columnNames.length; i++) {
			// 通过函数WritableFont（）设置字体样式
			// 第一个参数表示所选字体
			// 第二个参数表示字体大小
			// 第三个参数表示粗体样式，有BOLD和NORMAL两种样式
			// 第四个参数表示是否斜体,此处true表示为斜体
			// 第五个参数表示下划线样式
			// 第六个参数表示颜色样式，此处为Red
			this.addHeaderCell(columnNames[i]);
		}
	}

	public io.leopard.boot.excel.poi.Row addRow() {
		int rowCount = this.sheet.getLastRowNum();
		Row row = this.sheet.createRow(rowCount + 1);
		return new io.leopard.boot.excel.poi.Row(row, this.mainCellStyle);
	}

	public void save(File file) throws IOException {
		file.getParentFile().mkdirs();
		OutputStream stream = new FileOutputStream(file);
		this.workbook.write(stream);
		stream.close();
		this.workbook.close();
	}
}
