package io.leopard.boot.excel;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class RowJxlImpl extends Row {

	private WritableSheet sheet;

	private int rowNumber;

	private int currentCell;

	public RowJxlImpl(WritableSheet sheet, int rowNumber) {
		this.sheet = sheet;
		this.rowNumber = rowNumber;
		this.currentCell = 0;
	}

	@Override
	public RowJxlImpl addCell(String str) {
		try {
			sheet.addCell(new Label(currentCell++, rowNumber, str));
		}
		catch (RowsExceededException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (WriteException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return this;
	}

}
