package io.leopard.boot.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import jxl.Workbook;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class SheetTest {

	@Test
	public void Sheet() throws IOException, WriteException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		WritableWorkbook workbook = Workbook.createWorkbook(output);

		Sheet sheet = new Sheet(workbook, "sheet1");
		sheet.addColumnName("test1", "test2");
		sheet.addColumn("test3", 100);

		sheet.addRow().addCell("str1str1str1str1str1").addCell("str2str2str2str2str2str2").addCell("str3str3str3str3str3");
		workbook.write();
		workbook.close();

		FileUtils.writeByteArrayToFile(new File("/tmp/test1.xls"), output.toByteArray());
	}

}