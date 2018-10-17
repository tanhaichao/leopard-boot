package io.leopard.boot.onum.dynamic.service;

import java.util.List;

import org.junit.Test;

import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantEntity;
import io.leopard.boot.onum.dynamic.model.Operator;
import io.leopard.json.Json;

public class DynamicEnumDaoMemoryImplTest {

	private DynamicEnumDaoMemoryImpl dynamicEnumDaoMemoryImpl = new DynamicEnumDaoMemoryImpl();

	@Test
	public void add() {
		Operator operator = new Operator();
		operator.setOperator("adminId", 1);
		DynamicEnumConstantEntity record = new DynamicEnumConstantEntity();
		record.setKey("a1");
		record.setDesc("A1");
		record.setEnumId("Gender");
		this.dynamicEnumDaoMemoryImpl.add(record, operator);

		List<DynamicEnumConstantEntity> recordList = this.dynamicEnumDaoMemoryImpl.list("Gender");
		Json.printList(recordList, "recordList");

	}

}