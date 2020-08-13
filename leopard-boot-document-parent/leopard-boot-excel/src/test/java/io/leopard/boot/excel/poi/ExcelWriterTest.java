package io.leopard.boot.excel.poi;

import java.io.File;
import java.io.IOException;
import java.util.Date;

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
		row.addCell("广州广州广州广州广州广州广州广州广州广州广州广州广州广州广州广州");
		row.addCell(2);
		row.addDate(new Date());
		writer.autoSizeColumns();
		writer.save(new File("/tmp/excel/test.xls"));
	}

}