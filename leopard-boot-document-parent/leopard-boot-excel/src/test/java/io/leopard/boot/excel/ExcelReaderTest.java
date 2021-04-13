package io.leopard.boot.excel;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ExcelReaderTest {

	@Test
	public void toList() throws IOException {
		File file = new File("/Users/tanhaichao/Documents/test1.xlsx");
		Map<String, String> columnMapping = new LinkedHashMap<String, String>();
		columnMapping.put("doctorId", "医生ID");
		columnMapping.put("idcardNumber", "身份证号");
		columnMapping.put("expiryDate", "有效期");

		List<DoctorInsuranceForm> doctorList = ExcelReader.toList(file, DoctorInsuranceForm.class, columnMapping);
		for (DoctorInsuranceForm form : doctorList) {

		}
	}

	/**
	 * 医责险
	 */
	public static class DoctorInsuranceForm {

		/**
		 * 医生ID
		 */
		private long doctorId;

		/**
		 * 身份证号
		 */
		private String idcardNumber;

		/**
		 * 有效期
		 */
		private Date expiryDate;

		/**
		 * 姓名
		 */
		private String name;

		public long getDoctorId() {
			return this.doctorId;
		}

		public void setDoctorId(long doctorId) {
			this.doctorId = doctorId;
		}

		public String getIdcardNumber() {
			return this.idcardNumber;
		}

		public void setIdcardNumber(String idcardNumber) {
			this.idcardNumber = idcardNumber;
		}

		public Date getExpiryDate() {
			return this.expiryDate;
		}

		public void setExpiryDate(Date expiryDate) {
			this.expiryDate = expiryDate;
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}