package io.leopard.boot.onum.dynamic.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantEntity;
import io.leopard.boot.onum.dynamic.model.Operator;
import io.leopard.json.Json;
import io.leopard.test.IntegrationTests;

public class DynamicEnumServiceImplTest extends IntegrationTests {

	@Autowired
	private DynamicEnumServiceImpl dynamicEnumService;

	@Test
	@Transactional
	public void add() {
		Operator operator = new Operator();
		operator.setOperator("adminId", 1);
		DynamicEnumConstantEntity record = new DynamicEnumConstantEntity();
		record.setKey("a1");
		record.setDesc("A1");
		record.setEnumId("Gender");

		this.dynamicEnumService.add(record, operator);

		List<DynamicEnumConstantEntity> recordList = this.dynamicEnumService.list("Gender");
		Json.printList(recordList, "recordList");
	}

}