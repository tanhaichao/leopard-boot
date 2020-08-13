package io.leopard.boot.excel;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

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
}
