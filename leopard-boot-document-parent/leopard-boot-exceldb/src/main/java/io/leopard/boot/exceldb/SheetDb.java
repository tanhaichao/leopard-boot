package io.leopard.boot.exceldb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class SheetDb {

	private List<Column> columnList = new ArrayList<>();

	private Workbook workbook;

	private Sheet sheet;

	private List<String> excelColumnNameList = new ArrayList<>();

	public SheetDb(File file, int sheetIndex) throws IOException {
		this.workbook = WorkbookUtil.open(file);
		this.sheet = workbook.getSheetAt(sheetIndex);
		this.init();
	}

	public SheetDb(File file, String sheetName) throws IOException {
		this.workbook = WorkbookUtil.open(file);
		this.sheet = workbook.getSheet(sheetName);
		this.init();
	}

	private void init() {
		Row headerRow = sheet.getRow(0);
		for (int i = 0; i < headerRow.getLastCellNum(); i++) {
			String excelColumnName = headerRow.getCell(i).getStringCellValue();
			excelColumnNameList.add(excelColumnName);
		}
	}

	/**
	 * 添加列
	 * 
	 * @param excelColumnName Excel列名
	 * @param dbColumnName
	 */
	public void addColumn(String excelColumnName, String dbColumnName) {
		int columnIndex = excelColumnNameList.indexOf(excelColumnName);
		if (columnIndex == -1) {
			throw new RuntimeException("列[" + excelColumnName + "]不存在.");
		}
		columnList.add(new Column(columnIndex, excelColumnName, dbColumnName));
	}

	public void test2() {
		Row row = this.sheet.getRow(0);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);
			System.out.println(i + ":" + cell.getStringCellValue());
		}
	}

	public String toInsertSql(String tableName) {
		return this.toInsertSql(tableName, -1);
	}

	public String toInsertSql(String tableName, int rowCount) {
		StringBuilder sb = new StringBuilder();
		int lastRowNum = this.sheet.getLastRowNum();
		if (rowCount > 0) {
			if (rowCount < lastRowNum) {
				lastRowNum = rowCount + 1;
			}
		}
		Set<String> nameSet = new LinkedHashSet<>();
		for (int i = 0; i < columnList.size(); i++) {
			Column column = columnList.get(i);
			nameSet.add("`" + column.getDbColumnName() + "`");
		}
		String names = "insert into `" + tableName + "`(" + StringUtils.join(nameSet, ", ") + ") values(";

		for (int i = 1; i < lastRowNum; i++) {
			Row row = sheet.getRow(i);
			Set<String> valueSet = this.rowToValueSet(i, row);
			sb.append(names);
			sb.append(StringUtils.join(valueSet, ", ")).append(");\n");
		}
		return sb.toString();
	}

	private Set<String> rowToValueSet(int rowIndex, Row row) {
		Set<String> valueSet = new LinkedHashSet<>();
		for (int i = 0; i < columnList.size(); i++) {
			Column column = columnList.get(i);
			Cell cell = row.getCell(column.getColumnIndex());
			String value;
			if (cell == null) {
				value = "";
				System.err.println(" rowIndex:" + rowIndex + " cell:" + column.getColumnIndex() + " is null.");
			}
			else {
				value = cell.getStringCellValue();
			}
			valueSet.add("'" + value + "'");
		}
		return valueSet;
	}

	public void close() throws IOException {
		workbook.close();
	}

	public void test() {
		int lastRowNum = sheet.getLastRowNum();
		System.out.println("lastRowNum:" + lastRowNum);
	}

	private static class Column {
		private final String excelColumnName;

		private final String dbColumnName;

		private int columnIndex;

		public Column(int columnIndex, String excelColumnName, String dbColumnName) {
			this.columnIndex = columnIndex;
			this.excelColumnName = excelColumnName;
			this.dbColumnName = dbColumnName;
		}

		public String getExcelColumnName() {
			return excelColumnName;
		}

		public String getDbColumnName() {
			return dbColumnName;
		}

		public int getColumnIndex() {
			return columnIndex;
		}

		public void setColumnIndex(int columnIndex) {
			this.columnIndex = columnIndex;
		}

	}
}
