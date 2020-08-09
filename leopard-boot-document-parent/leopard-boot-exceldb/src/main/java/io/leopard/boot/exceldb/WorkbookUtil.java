package io.leopard.boot.exceldb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkbookUtil {

	public static Workbook open(File file) throws IOException {
		Workbook workbook;
		if (file.getName().endsWith(".xlsx")) {
			workbook = new XSSFWorkbook(new FileInputStream(file));
		}
		else {
			workbook = new HSSFWorkbook(new FileInputStream(file));
		}
		return workbook;
	}
}
