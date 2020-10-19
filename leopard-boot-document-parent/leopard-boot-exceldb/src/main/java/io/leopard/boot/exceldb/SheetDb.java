package io.leopard.boot.exceldb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import io.leopard.lang.inum.EnumUtil;
import io.leopard.lang.inum.Onum;

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

	public Sheet getSheet() {
		return sheet;
	}

	/**
	 * 添加列
	 * 
	 * @param excelColumnName Excel列名
	 * @param dbColumnName
	 */
	public Column addColumn(String excelColumnName, String dbColumnName) {
		return this.addColumn(excelColumnName, dbColumnName, String.class);
	}

	public Column addColumn(String excelColumnName, String dbColumnName, Class<?> columnValueType) {
		int columnIndex = excelColumnNameList.indexOf(excelColumnName);
		if (columnIndex == -1) {
			throw new RuntimeException("列[" + excelColumnName + "]不存在.");
		}
		Column column = new Column(columnIndex, dbColumnName, columnValueType);
		columnList.add(column);
		return column;
	}

	public void test2() {
		Row row = this.sheet.getRow(0);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);
			System.out.println(i + ":" + cell.getStringCellValue());
		}
	}

	public List<Map<String, String>> toListMap() {
		return toListMap(-1);
	}

	public List<Map<String, String>> toListMap(int rowCount) {
		int lastRowNum = this.sheet.getLastRowNum();
		if (rowCount > 0) {
			if (rowCount < lastRowNum) {
				lastRowNum = rowCount;
			}
		}
		System.err.println("lastRowNum:" + lastRowNum);
		List<Map<String, String>> list = new ArrayList<>();
		for (int i = 1; i <= lastRowNum; i++) {
			Row row = sheet.getRow(i);
			Map<String, String> map = this.rowToMap(i, row);
			list.add(map);
		}
		return list;
	}

	private Map<String, String> rowToMap(int rowIndex, Row row) {
		Map<String, String> map = new LinkedHashMap<>();
		for (int i = 0; i < columnList.size(); i++) {
			Column column = columnList.get(i);
			String value = this.getColumnValue(rowIndex, row, column);
			map.put(column.getDbColumnName(), value);
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String getColumnValue(int rowIndex, Row row, Column column) {
		Cell cell = row.getCell(column.getColumnIndex());
		String value;
		if (cell == null) {
			value = "";
			System.err.println(" rowIndex:" + rowIndex + " cell:" + column.getColumnIndex() + " is null.");
		}
		else {
			Class<?> valueType = column.getColumnValueType();
			if (valueType.equals(Long.class)) {
				// System.err.println("column:" + column.getDbColumnName() + " rowIndex:" + rowIndex);
				CellType cellType = cell.getCellType();
				if (cellType == CellType.STRING) {
					value = Long.parseLong(cell.getStringCellValue().trim()) + "";
				}
				else if (cellType == CellType.NUMERIC) {
					value = ((long) cell.getNumericCellValue()) + "";
				}
				else {
					throw new RuntimeException("未知单元格类型[" + cellType.name() + "].");
				}
			}
			else {
				value = cell.getStringCellValue();
			}
		}
		if (column.getOnumClazz() != null) {// 枚举转换
			// System.err.println("value:" + value + " rowIndex:" + rowIndex);
			Onum onum = (Onum) EnumUtil.toEnumByDesc(value, (Class<Enum>) column.getOnumClazz());
			value = (String) onum.getKey();
		}
		return value;
	}

	public String toInsertSql(String tableName) {
		return this.toInsertSql(tableName, -1);
	}

	public String toInsertSql(String tableName, int rowCount) {
		StringBuilder sb = new StringBuilder();
		int lastRowNum = this.sheet.getLastRowNum();
		if (rowCount > 0) {
			if (rowCount < lastRowNum) {
				lastRowNum = rowCount;
			}
		}
		List<String> nameList = new ArrayList<>();
		for (int i = 0; i < columnList.size(); i++) {
			Column column = columnList.get(i);
			nameList.add("`" + column.getDbColumnName() + "`");
		}
		String names = "insert into `" + tableName + "`(" + StringUtils.join(nameList, ", ") + ") values(";

		for (int i = 1; i <= lastRowNum; i++) {
			Row row = sheet.getRow(i);
			List<String> valueList = this.rowToValueList(i, row);
			sb.append(names);
			sb.append(StringUtils.join(valueList, ", ")).append(");\n");
		}
		return sb.toString();
	}

	private List<String> rowToValueList(int rowIndex, Row row) {
		List<String> valueList = new ArrayList<>();
		for (int i = 0; i < columnList.size(); i++) {
			Column column = columnList.get(i);
			String value = this.getColumnValue(rowIndex, row, column);
			//
			// Cell cell = row.getCell(column.getColumnIndex());
			// String value;
			// if (cell == null) {
			// value = "";
			// System.err.println(" rowIndex:" + rowIndex + " cell:" + column.getColumnIndex() + " is null.");
			// }
			// else {
			// Class<?> valueType = column.getColumnValueType();
			// if (valueType.equals(Long.class)) {
			// value = ((long) cell.getNumericCellValue()) + "";
			// }
			// else {
			// value = cell.getStringCellValue();
			// }
			// }
			valueList.add("'" + value + "'");
		}
		return valueList;
	}

	public void close() throws IOException {
		workbook.close();
	}

	public void test() {
		int lastRowNum = sheet.getLastRowNum();
		System.out.println("lastRowNum:" + lastRowNum);
	}

	public static class Column {

		private final String dbColumnName;

		private final int columnIndex;

		private final Class<?> columnValueType;// 默认为字符串

		private Class<? extends Enum<?>> onumClazz;// 枚举

		public Column(int columnIndex, String dbColumnName, Class<?> columnValueType) {
			this.columnIndex = columnIndex;
			this.dbColumnName = dbColumnName;
			this.columnValueType = columnValueType;
		}

		public String getDbColumnName() {
			return dbColumnName;
		}

		public int getColumnIndex() {
			return columnIndex;
		}

		public Class<?> getColumnValueType() {
			return columnValueType;
		}

		public Class<?> getOnumClazz() {
			return onumClazz;
		}

		public void setOnumClazz(Class<? extends Enum<?>> onumClazz) {
			this.onumClazz = onumClazz;
		}

		public Column onumClazz(Class<? extends Enum<?>> onumClazz) {
			this.onumClazz = onumClazz;
			return this;
		}

	}
}
