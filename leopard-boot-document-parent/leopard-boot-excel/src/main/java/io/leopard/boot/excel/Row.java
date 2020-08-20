package io.leopard.boot.excel;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import io.leopard.lang.inum.Bnum;
import io.leopard.lang.inum.Inum;
import io.leopard.lang.inum.Snum;

public class Row {

	private org.apache.poi.ss.usermodel.Row row;

	private CellStyle style;

	private CellStyle dateCellStyle;

	public Row(org.apache.poi.ss.usermodel.Row row, CellStyle style, CellStyle dateCellStyle) {
		this.row = row;
		this.style = style;
		this.dateCellStyle = dateCellStyle;
	}

	public Row skipCell(int count) {
		int column = this.row.getLastCellNum();
		if (column == -1) {
			column = 0;
		}
		for (int i = 0; i < count; i++) {
			Cell cell = row.createCell(column + i);
			cell.setCellStyle(style);
		}
		return this;
	}

	public Row addCell(String value) {
		int column = this.row.getLastCellNum();
		if (column == -1) {
			column = 0;
		}
		Cell cell = row.createCell(column);
		cell.setCellStyle(style);
		cell.setCellValue(value);
		return this;
	}

	public Row setCell(int column, double value) {
		Cell cell = row.createCell(column);
		cell.setCellStyle(style);
		cell.setCellValue(value);
		return this;
	}

	public Row setId(int column, String id) {
		Cell cell = row.createCell(column);
		cell.setCellStyle(style);
		cell.setCellValue(id);
		return this;
	}

	public Row setId(int column, int id) {
		Cell cell = row.createCell(column);
		cell.setCellStyle(style);
		cell.setCellValue(Integer.toString(id));
		return this;
	}

	public Row setId(int column, long id) {
		Cell cell = row.createCell(column);
		cell.setCellStyle(style);
		cell.setCellValue(Long.toString(id));
		return this;
	}

	public Row setCell(int column, long value) {
		Cell cell = row.createCell(column);
		cell.setCellStyle(style);
		cell.setCellValue(value);
		return this;
	}

	public Row setCell(int column, String value) {
		Cell cell = row.createCell(column);
		cell.setCellStyle(style);
		cell.setCellValue(value);
		return this;
	}

	public Row setCell(int column, Snum snum) {
		String value = snum == null ? "" : snum.getDesc();
		Cell cell = row.createCell(column);
		cell.setCellStyle(style);
		cell.setCellValue(value);
		return this;
	}

	public Row addCell(Bnum bnum) {
		String str = bnum == null ? "" : bnum.getDesc();
		return this.addCell(str);
	}

	public Row addCell(Inum inum) {
		String str = inum == null ? "" : inum.getDesc();
		return this.addCell(str);
	}

	public Row addCell(Snum snum) {
		String str = snum == null ? "" : snum.getDesc();
		return this.addCell(str);
	}

	public Row addCell(double num) {
		// String str = Double.toString(num);
		// return this.addCell(str);
		int column = this.row.getLastCellNum();
		if (column == -1) {
			column = 0;
		}
		Cell cell = row.createCell(column);
		cell.setCellStyle(style);
		cell.setCellValue(num);
		return this;
	}

	public Row addCell(float num) {
		return this.addCell((double) num);
	}

	public Row addCell(int num) {
		return this.addCell((double) num);
	}

	public Row addCell(long num) {
		return this.addCell((double) num);
	}

	public Row addId(String id) {
		return this.addCell(id);
	}

	public Row addId(int id) {
		return this.addCell(Integer.toString(id));
	}

	public Row addId(long id) {
		return this.addCell(Long.toString(id));
	}

	/**
	 * 自动在数字前增加+号？
	 * 
	 * @param number
	 * @return
	 */
	public Row addFormatCell(double number) {
		String str;
		if (number > 0) {
			str = "+" + number;
		}
		else {
			str = number + "";
		}
		return this.addCell(str);
	}

	public Row addCell(Date date) {
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		return this.addCell(time);
	}

	public Row addDate(Date date) {
		int column = this.row.getLastCellNum();
		if (column == -1) {
			column = 0;
		}
		Cell cell = row.createCell(column);
		cell.setCellStyle(dateCellStyle);
		cell.setCellValue(date);
		return this;
	}

	/**
	 * 合并行
	 * 
	 * @param column 第几列
	 * @param rowCount 行数
	 */
	public void mergeRows(int column, int rowCount) {
		int firstRow = this.row.getRowNum();
		int lastRow = firstRow + rowCount - 1;
		CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, column, column);
		Sheet sheet = this.row.getSheet();
		sheet.addMergedRegion(region);
	}

	public Row getRow(int index) {
		Sheet sheet = this.row.getSheet();
		org.apache.poi.ss.usermodel.Row row;
		if (index == 0) {
			row = this.row;
		}
		else if (index < 0) {
			row = sheet.getRow(this.row.getRowNum() + index);
		}
		else {
			row = sheet.createRow(this.row.getRowNum() + index);
		}
		return new Row(row, style, dateCellStyle);

	}
}
