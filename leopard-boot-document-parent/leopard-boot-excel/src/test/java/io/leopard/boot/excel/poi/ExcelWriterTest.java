package io.leopard.boot.excel.poi;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class ExcelWriterTest {

	@Test
	public void ExcelWriter() throws IOException {
		ExcelWriter writer = new ExcelWriter();
		writer.createSheet("医院");
		writer.addColumnName("名称", "省份", "城市");
		writer.addHeaderCell("排序");

		Row row = writer.addRow();
		row.addCell("名称1");
		row.addCell("广东");
		row.addCell("广州广州广州广州广州广州广州广州广州广州广州广州广州广州广州广州");
		writer.save(new File("/tmp/excel/test.xls"));
	}

}