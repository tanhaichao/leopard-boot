package io.leopard.boot.mvc.mock;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.leopard.boot.mvc.mock.asserter.AssertController;
import io.leopard.boot.mvc.mock.asserter.Asserter;

public class MockWeb {
	private MockMvc mvc;

	public MockWeb(WebApplicationContext context) {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();// 建议使用这种
	}

	public MockAction perform(RequestBuilder requestBuilder) throws Exception {
		ResultActions actions = mvc.perform(requestBuilder);
		return new MockAction(actions);
	}

	public MockAction perform(AssertController controller, Asserter asserter) throws Exception {
		// MockAction action = web.perform(MockMvcRequestBuilders.get("/leopardboot/RequestMapping/get2").param("lat", "123.123").param("lon", "456.456").accept(MediaType.APPLICATION_JSON));
		String urlTemplate = asserter.getUrl();
		// Class<?> controllerClazz = controller.getClass();

		// if (StringUtils.isEmpty(url)) {
		// RequestMapping requestMapping = controllerClazz.getDeclaredAnnotation(RequestMapping.class);
		// }
		HttpMethod httpMethod = HttpMethod.valueOf(asserter.getHttpMethod());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.request(httpMethod, urlTemplate);

		if (asserter.getRequestBody() != null) {
			requestBuilder.contentType(MediaType.APPLICATION_JSON).content(asserter.getRequestBody());
		}

		MockAction action = perform(requestBuilder);

		System.err.println("asserter.getData():" + asserter.getData());
		if (!"None".equals(asserter.getData())) {
			action.body(asserter.getData());
		}
		if (!"None".equals(asserter.getData())) {
			action.data(asserter.getData());
		}
		return action;
	}
}
