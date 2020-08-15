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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelReader {

	public static <E> List<E> toList(File file, Class<E> clazz, Map<String, String> columnMapping) throws IOException {
		InputStream input = new FileInputStream(file);
		String fileName = file.getName();
		return toList(fileName, input, clazz, columnMapping);
	}

	public static <E> List<E> toList(MultipartFile file, Class<E> clazz, Map<String, String> columnMapping) throws IOException {
		InputStream input = file.getInputStream();
		String fileName = file.getOriginalFilename();
		return toList(fileName, input, clazz, columnMapping);
	}

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
		for (int i = 1; i < sheet.getLastRowNum(); i++) {
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
			org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);

			for (Entry<String, String> entry : columnMapping.entrySet()) {
				String fieldName = entry.getKey().trim();
				// String columnName = entry.getValue().trim();
				int columnIndex = headerMapping.get(fieldName);

				Cell cell = row.getCell(columnIndex);
				String value = cell.getStringCellValue();// FIXME 暂时只支持字符串
				try {
					Field field = clazz.getDeclaredField(fieldName);
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
		}
		workbook.close();
		input.close();

		return list;
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
