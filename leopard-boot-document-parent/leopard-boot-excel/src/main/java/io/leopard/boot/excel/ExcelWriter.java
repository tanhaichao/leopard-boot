package io.leopard.boot.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
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
		this(null, xlsx);
	}

	public ExcelWriter(String sheetName) {
		this(sheetName, false);
	}

	public ExcelWriter(String sheetName, boolean xlsx) {
		if (xlsx) {
			this.workbook = new XSSFWorkbook();
		}
		else {
			this.workbook = new HSSFWorkbook();
		}
		if (sheetName != null) {
			this.createSheet(sheetName);
		}
		this.init();
	}

	public Sheet createSheet(String sheetName) {
		this.sheet = workbook.createSheet(sheetName);
		return this.sheet;
	}

	public Sheet selectSheet(String sheetName) {
		this.sheet = this.workbook.getSheet(sheetName);
		return this.sheet;
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
		{// 日期格式
			Font font = this.workbook.createFont();
			font.setBold(false);
			font.setFontHeightInPoints((short) 11);
			font.setFontName("宋体");

			CellStyle style = workbook.createCellStyle();
			DataFormat format = workbook.createDataFormat();
			style.setDataFormat(format.getFormat("yyyy/mm/dd hh:mm:ss"));
			style.setFont(font);
			this.dateCellStyle = style;
		}
	}

	private CellStyle headerCellStyle;

	private CellStyle mainCellStyle;

	private CellStyle dateCellStyle;

	public void addHeaderCell(String columnName) {
		this.addHeaderCell(columnName, null);
	}

	public void addHeaderCell(String columnName, Integer columnWidth) {
		Row row = this.sheet.getRow(0);
		if (row == null) {
			row = this.sheet.createRow(0);
		}
		short columnIndex = row.getLastCellNum();
		if (columnIndex == -1) {
			columnIndex = 0;
		}
		// font.setFontHeight((short) 50);
		Cell cell = row.createCell(columnIndex);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(columnName);

		if (columnWidth != null) {
			this.sheet.setColumnWidth(columnIndex, columnWidth * 256);
		}

	}

	public void autoSizeColumns() {
		Row row = this.sheet.getRow(0);
		int columnCount = row.getLastCellNum();
		for (int i = 0; i < columnCount; i++) {
			sheet.autoSizeColumn(i);
			// System.out.println("width:" + sheet.getColumnWidth(i));
			sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 2 * 256);// 多出2个字
		}
	}

	public void autoSizeColumns(int columnIndex) {

		int rowCount = this.sheet.getLastRowNum();
		for (int i = 0; i <= rowCount; i++) {
			Row row = this.sheet.getRow(i);
			Cell cell = row.getCell(i);
			CellType cellType = cell.getCellType();
			// System.out.println("cellType:" + cellType.getCode());
			// this.sheet.autoSizeColumn(column);
		}
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
			this.addHeaderCell(columnNames[i], null);
		}
	}

	public io.leopard.boot.excel.Row addRow() {
		int rowCount = this.sheet.getLastRowNum();
		Row row = this.sheet.createRow(rowCount + 1);
		return new io.leopard.boot.excel.Row(row, this.mainCellStyle, this.dateCellStyle);
	}

	public void write(OutputStream stream) throws IOException {
		this.workbook.write(stream);
	}

	public void close() throws IOException {
		this.workbook.close();
	}

	public void save(File file) throws IOException {
		file.getParentFile().mkdirs();
		OutputStream stream = new FileOutputStream(file);
		this.workbook.write(stream);
		stream.close();
		this.workbook.close();
	}
}
