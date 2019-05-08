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

import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import jxl.Workbook;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelView extends ModelAndView {

	/**
	 * 文件名
	 */
	private String fileName;

	private ByteArrayOutputStream output;

	private WritableWorkbook workbook;

	protected String sheetName;

	private Sheet sheet;

	private int currentRow;

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
			workbook.write();
			workbook.close();

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
		output = new ByteArrayOutputStream();
		this.workbook = Workbook.createWorkbook(output);
		this.sheetName = sheetName;
	}

	public Sheet addSheet(String sheetName) {
		return new Sheet(workbook, sheetName);
	}

	/**
	 * 添加列
	 * 
	 * @param columnName 列名
	 * @param width 宽度,最大255
	 * @throws WriteException
	 */
	public void addColumn(String columnName, int width) throws WriteException {
		if (this.sheet == null) {
			this.sheet = new Sheet(workbook, sheetName);
		}
		this.sheet.addColumn(columnName, width);
	}

	public void addColumnName(String... columnNames) throws WriteException {
		this.sheet = new Sheet(workbook, sheetName);
		this.sheet.addColumnName(columnNames);
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Row addRow() {
		// Row row = new Row(sheet, currentRow);
		// currentRow++;
		// return row;
		return sheet.addRow();
	}

	public File save(String fileId) throws IOException, WriteException {
		File file = new File("/tmp/" + fileId + ".xls");
		this.save(file);
		return file;
	}

	public void save(File file) throws IOException, WriteException {
		workbook.write();
		workbook.close();
		FileUtils.writeByteArrayToFile(file, output.toByteArray());
	}

}
