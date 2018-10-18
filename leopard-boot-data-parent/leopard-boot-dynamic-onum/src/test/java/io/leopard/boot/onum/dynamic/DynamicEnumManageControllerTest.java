package io.leopard.boot.onum.dynamic;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantForm;
import io.leopard.boot.onum.dynamic.model.DynamicEnumForm;
import io.leopard.test.IntegrationTests;

public class DynamicEnumManageControllerTest extends IntegrationTests {

	@Autowired
	private DynamicEnumManageController dynamicEnumManageController;

	@Test
	@Transactional
	public void batchUpdate() throws DynamicEnumNotFoundException, Exception {
		DynamicEnumForm form = new DynamicEnumForm();
		form.setEnumId("deliveryMode");
		List<DynamicEnumConstantForm> constantList = new ArrayList<>();
		form.setConstantList(constantList);
		{
			DynamicEnumConstantForm constant = new DynamicEnumConstantForm();
			constant.setKey("option");
			constant.setDesc("选项");
			constantList.add(constant);
		}
		{
			DynamicEnumConstantForm constant = new DynamicEnumConstantForm();
			constant.setKey("666");
			constant.setDesc("xxxx");
			constantList.add(constant);
		}
		dynamicEnumManageController.batchUpdate(form, null);
	}

}