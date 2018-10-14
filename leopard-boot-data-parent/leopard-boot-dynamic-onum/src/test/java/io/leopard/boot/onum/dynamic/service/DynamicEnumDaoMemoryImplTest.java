package io.leopard.boot.onum.dynamic.service;

import java.util.List;

import org.junit.Test;

import io.leopard.boot.onum.dynamic.model.DynamicEnumEntity;
import io.leopard.boot.onum.dynamic.model.Operator;
import io.leopard.json.Json;

public class DynamicEnumDaoMemoryImplTest {

	private DynamicEnumDaoMemoryImpl dynamicEnumDaoMemoryImpl = new DynamicEnumDaoMemoryImpl();

	@Test
	public void add() {
		Operator operator = new Operator();
		operator.setOperator("adminId", 1);
		DynamicEnumEntity record = new DynamicEnumEntity();
		record.setKey("a1");
		record.setDesc("A1");
		record.setEnumId("Gender");
		this.dynamicEnumDaoMemoryImpl.add(record, operator);

		List<DynamicEnumEntity> recordList = this.dynamicEnumDaoMemoryImpl.list("Gender");
		Json.printList(recordList, "recordList");

	}

}