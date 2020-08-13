package io.leopard.boot.excel.poi;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import io.leopard.lang.inum.Bnum;
import io.leopard.lang.inum.Inum;
import io.leopard.lang.inum.Snum;
import jxl.write.WriteException;

public class Row {

	private org.apache.poi.ss.usermodel.Row row;

	private CellStyle style;

	public Row(org.apache.poi.ss.usermodel.Row row, CellStyle style) {
		this.row = row;
		this.style = style;
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

	public Row addCell(Bnum bnum) throws WriteException {
		String str = bnum.getDesc();
		return this.addCell(str);
	}

	public Row addCell(Inum inum) throws WriteException {
		String str = inum.getDesc();
		return this.addCell(str);
	}

	public Row addCell(Snum snum) throws WriteException {
		String str = snum.getDesc();
		return this.addCell(str);
	}

	public Row addCell(double num) throws WriteException {
		String str = Double.toString(num);
		return this.addCell(str);
	}

	public Row addCell(float num) throws WriteException {
		String str = Float.toString(num);
		return this.addCell(str);
	}

	public Row addCell(int num) throws WriteException {
		String str = Integer.toString(num);
		return this.addCell(str);
	}

	public Row addCell(long num) throws WriteException {
		String str = Long.toString(num);
		return this.addCell(str);
	}

	public Row addFormatCell(double number) throws WriteException {
		String str;
		if (number > 0) {
			str = "+" + number;
		}
		else {
			str = number + "";
		}
		return this.addCell(str);
	}

	public Row addCell(Date date) throws WriteException {
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		return this.addCell(time);
	}

}
