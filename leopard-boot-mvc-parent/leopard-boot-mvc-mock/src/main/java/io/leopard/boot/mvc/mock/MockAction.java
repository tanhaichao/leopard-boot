package io.leopard.boot.mvc.mock;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import org.hamcrest.Matchers;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import io.leopard.boot.responsebody.ResponseEntity;
import io.leopard.json.Json;

public class MockAction {

	private ResultActions actions;

	public MockAction(ResultActions actions) {
		this.actions = actions;
	}

	public void body(String str) throws Exception {
		ResponseEntity entity = new ResponseEntity();
		entity.setStatus("200");
		entity.setData(str);
		String json = Json.toJson(entity);
		actions.andExpect(MockMvcResultMatchers.content().string(Matchers.equalTo(json)));
	}

	public void body(Object data) throws Exception {
		ResponseEntity entity = new ResponseEntity();
		entity.setStatus("200");
		entity.setData(data);
		String json = Json.toJson(entity);
		System.err.println("body json:" + json);
		// actions.andExpect(MockMvcResultMatchers.content().string(Matchers.equalTo(json)));

		actions.andExpect(new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				String responseBody = result.getResponse().getContentAsString();

				System.err.println("response body:" + responseBody + " getContentAsString:" + result.getResponse().getContentAsString());
				assertEquals("Response Body验证", responseBody, json);
			}
		});
	}

	public void data(Object data) throws Exception {
		actions.andExpect(new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				String json = result.getResponse().getContentAsString();
				System.err.println("response body:" + json);
				ResponseEntity entity = Json.toObject(json, ResponseEntity.class);

				String json1 = Json.toJson(entity.getData());
				String json2 = Json.toJson(data);

				System.err.println("json1:" + json1);
				System.err.println("json2:" + json2);
				assertEquals("Json data字段验证", json1, json2);
			}
		});
	}
}
