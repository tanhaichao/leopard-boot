package io.leopard.boot.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

public class ExcelWriterTest {

	@Test
	public void ExcelWriter() throws IOException {
		ExcelWriter writer = new ExcelWriter();
		writer.createSheet("医院");
		writer.addColumnName("名称");
		writer.addHeaderCell("省份");
		writer.addHeaderCell("城市");
		writer.addColumnName("排序");
		writer.addHeaderCell("添加时间", 25);

		Row row = writer.addRow();
		row.addCell("名称1");
		row.addCell("广东");
		row.addCell("广州广州广州广州广州广州广州广州");
		// row.addCell("广州广州广州广州广州广州广州广州广州广州广州广州广州广州广州广州");
		row.addCell(2);
		row.addDate(new Date());
		writer.autoSizeColumns();
		writer.save(new File("/tmp/excel/test.xls"));
	}

	@Test
	public void test2() throws IOException {
		ExcelWriter writer = new ExcelWriter();
		writer.createSheet("订单");

		writer.addColumnName("订单编号");
		writer.addHeaderCell("订单状态");

		writer.addHeaderCell("药品ID");
		writer.addHeaderCell("药品名称");
		writer.addHeaderCell("规格");
		writer.addHeaderCell("药品数量");
		writer.addColumnName("金额");

		writer.addHeaderCell("运费", 25);
		writer.addHeaderCell("总金额", 25);
		writer.addHeaderCell("快递公司", 25);
		writer.addHeaderCell("快递单号", 25);

		Row row = writer.addRow();
		row.addCell("E-001");
		row.addCell("已付款");
		row.skipCell(5);
		// row.addCell("药品ID0");
		// row.addCell("药品名称0");
		// row.addCell(10);
		row.addCell(11);
		row.addCell(12);

		for (int i = 0; i < 3; i++) {
			Row _row = row.getRow(i);
			_row.setCell(2, "药品ID" + i);
			_row.setCell(3, "药品名称" + i);
		}

		row.mergeRows(0, 3);
		row.mergeRows(1, 3);
		row.mergeRows(7, 3);
		row.mergeRows(8, 3);
		row.mergeRows(9, 3);
		row.mergeRows(10, 3);

		// row.addCell("广东");
		// row.addCell("广州广州广州广州广州广州广州广州");
		// // row.addCell("广州广州广州广州广州广州广州广州广州广州广州广州广州广州广州广州");
		// row.addCell(2);
		// row.addDate(new Date());

		writer.autoSizeColumns();
		writer.save(new File("/tmp/excel/test.xls"));
	}

	@Test
	public void test() throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		HSSFSheet sheet = workbook.createSheet("sheet");

		HSSFRow row0 = sheet.createRow(0);
		HSSFCell cell_00 = row0.createCell(0);
		cell_00.setCellStyle(style);
		cell_00.setCellValue("日期");
		HSSFCell cell_01 = row0.createCell(1);
		cell_01.setCellStyle(style);
		cell_01.setCellValue("午别");

		HSSFRow row1 = sheet.createRow(1);
		HSSFCell cell_10 = row1.createCell(0);
		cell_10.setCellStyle(style);
		cell_10.setCellValue("20180412");
		HSSFCell cell_11 = row1.createCell(1);
		cell_11.setCellStyle(style);
		cell_11.setCellValue("上午");

		HSSFRow row2 = sheet.createRow(2);
		HSSFCell cell_21 = row2.createCell(1);
		cell_21.setCellStyle(style);
		cell_21.setCellValue("下午");

		// 合并日期占两行(4个参数，分别为起始行，结束行，起始列，结束列)
		// 行和列都是从0开始计数，且起始结束都会合并
		// 这里是合并excel中日期的两行为一行
		CellRangeAddress region = new CellRangeAddress(1, 2, 0, 0);
		sheet.addMergedRegion(region);

		File file = new File("/tmp/excel/demo.xls");
		FileOutputStream fout = new FileOutputStream(file);
		workbook.write(fout);
		fout.close();
		workbook.close();
	}
}