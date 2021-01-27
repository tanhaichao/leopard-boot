package io.leopard.json;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class JsonJacksonImplTest {

	public static class User {
		private String username;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

	}

	@Test
	public void toListObject() {
		User user = new User();
		user.setUsername("hctan");
		List<User> list = new ArrayList<User>();
		list.add(user);
		JsonJacksonImpl jackson = new JsonJacksonImpl();
		String json = jackson.toJson(list);
		List<User> list2 = jackson.toListObject(json, User.class);
	}

	@Test
	public void toListObject2() {
		String json = "[21,129]";
		JsonJacksonImpl jackson = new JsonJacksonImpl();

		List<Long> list = jackson.toListObject(json, Long.class);
		System.out.println("list:" + list);
	}
}