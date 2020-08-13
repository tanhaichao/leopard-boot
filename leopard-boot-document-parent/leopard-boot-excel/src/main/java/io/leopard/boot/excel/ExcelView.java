package io.leopard.boot.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import jxl.write.WriteException;

public class ExcelView extends ModelAndView {

	/**
	 * 文件名
	 */
	private String fileName;

	// private ByteArrayOutputStream output;

	private ExcelWriter excelWriter;

	protected String sheetName;

	private AbstractUrlBasedView view = new AbstractUrlBasedView() {
		@Override
		protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
			response.setContentType("application/force-download");
			String fileName = getFileName();
			if (StringUtils.isEmpty(fileName)) {
				fileName = Long.toString(System.currentTimeMillis());
			}
			String filedisplay = URLEncoder.encode(fileName + ".xls", "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
			// System.out.println("filedisplay:" + filedisplay);

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			excelWriter.write(output);
			excelWriter.close();
			InputStream input = new ByteArrayInputStream(output.toByteArray());
			java.io.OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = input.read(b)) > 0) {
				out.write(b, 0, i);
			}
			input.close();
			out.flush();
			output.close();
		}
	};

	public ExcelView() throws IOException {
		this("sheet1");
	}

	public ExcelView(String sheetName) throws IOException {
		super.setView(view);
		this.excelWriter = new ExcelWriter(false);
		excelWriter.createSheet(sheetName);
		this.sheetName = sheetName;
	}

	public void addSheet(String sheetName) {
		excelWriter.createSheet(sheetName);

		// return new Sheet(workbook, sheetName);
	}

	/**
	 * 添加列
	 * 
	 * @param columnName 列名
	 * @param width 宽度,最大255
	 * @throws WriteException
	 */
	public void addColumn(String columnName, int columnWidth) throws WriteException {
		excelWriter.addHeaderCell(columnName, columnWidth);
		// this.sheet.addColumn(columnName, width);
	}

	public void addColumnName(String... columnNames) throws WriteException {
		excelWriter.addColumnName(columnNames);
		// this.sheet.addColumnName(columnNames);
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public io.leopard.boot.excel.Row addRow() {
		return excelWriter.addRow();
		// Row row = new Row(sheet, currentRow);
		// currentRow++;
		// return row;
		// return sheet.addRow();
	}

	public File save(String fileId) throws IOException, WriteException {
		File file = new File("/tmp/" + fileId + ".xls");
		this.save(file);
		return file;
	}

	public void save(File file) throws IOException, WriteException {
		this.excelWriter.save(file);
		// FileUtils.writeByteArrayToFile(file, output.toByteArray());
	}

}
