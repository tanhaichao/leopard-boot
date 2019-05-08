package io.leopard.boot.excel;

import jxl.CellView;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Sheet {

	private WritableSheet sheet;

	private int currentRow;

	public Sheet(WritableWorkbook workbook, String sheetName) {
		this.sheet = workbook.createSheet(sheetName, 0);
		this.currentRow = 1;
	}

	public void addColumnName(String... columnNames) throws WriteException {
		int columnCount = this.sheet.getColumns();
		// System.err.println("columnCount:" + columnCount);
		for (int i = 0; i < columnNames.length; i++) {
			// 通过函数WritableFont（）设置字体样式
			// 第一个参数表示所选字体
			// 第二个参数表示字体大小
			// 第三个参数表示粗体样式，有BOLD和NORMAL两种样式
			// 第四个参数表示是否斜体,此处true表示为斜体
			// 第五个参数表示下划线样式
			// 第六个参数表示颜色样式，此处为Red
			WritableFont wf = new WritableFont(WritableFont.TIMES, 11, WritableFont.BOLD);
			CellFormat cf = new WritableCellFormat(wf);
			Label label = new Label(i + columnCount, 0, columnNames[i], cf);
			sheet.addCell(label);

			CellView cellView = new CellView();
			cellView.setAutosize(true); // 设置自动大小
			// cellView.setSize(d);
			sheet.setColumnView(i, cellView);
		}
	}

	/**
	 * 添加列
	 * 
	 * @param columnName 列名
	 * @param width 宽度,最大255
	 * @throws WriteException
	 */
	public void addColumn(String columnName, int width) throws WriteException {
		if (width > 255) {
			throw new IllegalArgumentException("宽度最大为255[" + width + "].");
		}
		int columnCount = this.sheet.getColumns();

		WritableFont wf = new WritableFont(WritableFont.TIMES, 11, WritableFont.BOLD);
		CellFormat cf = new WritableCellFormat(wf);
		Label label = new Label(columnCount, 0, columnName, cf);
		sheet.addCell(label);

		// CellView cellView = new CellView();
		// cellView.setAutosize(true); // 设置自动大小
		// cellView.setSize(width);
		sheet.setColumnView(columnCount, width);
		// sheet.setColumnView(columnCount - 1, width);
	}

	public Row addRow() {
		Row row = new Row(sheet, currentRow);
		currentRow++;
		return row;
	}

	public int getColumns() {
		return sheet.getColumns();
	}

}
