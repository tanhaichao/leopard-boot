package io.leopard.boot.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	protected static Log logger = LogFactory.getLog(ExcelReader.class);

	public static List<String> getStringList(File file, String columnName) throws IOException {
		InputStream input = new FileInputStream(file);
		String fileName = file.getName();
		Workbook workbook = open(fileName, input);
		Sheet sheet = workbook.getSheetAt(0);
		org.apache.poi.ss.usermodel.Row header = sheet.getRow(0);
		int columnIndex = findColumnIndex(header, columnName);
		if (columnIndex == -1) {
			throw new IllegalArgumentException("列名[" + columnName + "]不存在.");
		}
		List<String> list = new ArrayList<>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
			Cell cell = row.getCell(columnIndex);
			if (cell == null) {
				logger.warn("第" + i + "行不合法.");
				continue;
			}
			String value = cell.getStringCellValue();
			list.add(value);
		}
		return list;
	}

	private static int findColumnIndex(org.apache.poi.ss.usermodel.Row row, String columnName) {
		for (int i = 0; i < row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);
			String _columnName = cell.getStringCellValue().trim();
			if (columnName.equals(_columnName)) {
				return i;
			}
		}
		return -1;
	}

	public static <E> List<E> toList(File file, Class<E> clazz, Map<String, String> columnMapping) throws IOException {
		InputStream input = new FileInputStream(file);
		String fileName = file.getName();
		return toList(fileName, input, clazz, columnMapping);
	}

	// public static <E> List<E> toList(MultipartFile file, Class<E> clazz, Map<String, String> columnMapping) throws IOException {
	// InputStream input = file.getInputStream();
	// String fileName = file.getOriginalFilename();
	// return toList(fileName, input, clazz, columnMapping);
	// }

	public static <E> List<E> toList(String fileName, InputStream input, Class<E> clazz, Map<String, String> columnMapping) throws IOException {
		Workbook workbook = open(fileName, input);
		Sheet sheet = workbook.getSheetAt(0);

		Map<String, Integer> headerMapping = new LinkedHashMap<>();
		{// 计算列头映射关系
			org.apache.poi.ss.usermodel.Row header = sheet.getRow(0);
			List<String> headerColumnNameList = new ArrayList<>();
			for (int i = 0; i < header.getLastCellNum(); i++) {
				Cell cell = header.getCell(i);
				String columnName = cell.getStringCellValue().trim();
				headerColumnNameList.add(columnName);
			}

			for (Entry<String, String> entry : columnMapping.entrySet()) {
				String fieldName = entry.getKey();
				String columnName = entry.getValue();
				int columnIndex = headerColumnNameList.indexOf(columnName);
				if (columnIndex == -1) {
					throw new RuntimeException("列名[" + columnName + "]找不到.");
				}
				headerMapping.put(fieldName, columnIndex);
			}
		}

		List<E> list = new ArrayList<>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
			boolean isValidRow = isValidRow(row, headerMapping);
			if (!isValidRow) {
				logger.warn("第" + i + "行不合法.");
				continue;
			}
			E bean;
			try {
				bean = clazz.newInstance();
			}
			catch (InstantiationException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			}

			for (Entry<String, String> entry : columnMapping.entrySet()) {
				String fieldName = entry.getKey().trim();
				// String columnName = entry.getValue().trim();
				int columnIndex = headerMapping.get(fieldName);

				Cell cell = row.getCell(columnIndex);
				try {
					Field field = clazz.getDeclaredField(fieldName);
					Class<?> fieldType = field.getType();
					Object value = getCellValue(cell, fieldType);

					field.setAccessible(true);
					field.set(bean, value);
				}
				catch (NoSuchFieldException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				catch (SecurityException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}

			list.add(bean);
		}
		workbook.close();
		input.close();

		return list;
	}

	private static Object getCellValue(Cell cell, Class<?> type) {
		if (String.class.equals(type)) {
			return cell.getStringCellValue();
		}
		else if (type.equals(double.class)) {
			return cell.getNumericCellValue();
		}
		else {
			throw new RuntimeException("未支持该数据类型[" + type.getName() + "]。");
		}

	}

	/**
	 * 是否合法行
	 * 
	 * @param row
	 * @return
	 */
	protected static boolean isValidRow(org.apache.poi.ss.usermodel.Row row, Map<String, Integer> headerMapping) {
		for (Integer columnIndex : headerMapping.values()) {
			Cell cell = row.getCell(columnIndex);
			if (cell == null) {
				return false;
			}
		}
		return true;
	}

	public static Workbook open(String fileName, InputStream input) throws IOException {
		Workbook workbook;
		if (fileName.endsWith(".xlsx")) {
			workbook = new XSSFWorkbook(input);
		}
		else {
			workbook = new HSSFWorkbook(input);
		}
		return workbook;
	}
}
