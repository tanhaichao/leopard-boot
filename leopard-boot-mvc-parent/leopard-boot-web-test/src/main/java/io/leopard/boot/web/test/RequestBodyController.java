package io.leopard.boot.web.test;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.boot.mvc.mock.asserter.AssertController;
import io.leopard.boot.mvc.mock.asserter.AssertManager;

/**
 * RequestBody
 * 
 * @author 谭海潮
 *
 */
@Controller
@RequestMapping("/leopardboot/RequestBody/")
public class RequestBodyController implements AssertController {

	@RequestMapping
	@ResponseBody
	public int uid(int uid, @RequestBody(required = false) User user) {
		if (user != null) {
			return user.getUid();
		}
		else {
			return uid;
		}
	}

	@RequestMapping
	@ResponseBody
	public List<String> updateArticle(int uid, Article form) {
		return form.getDeletedCommentIdList();
	}

	public static class User {
		int uid;

		public int getUid() {
			return uid;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}

	}

	public static class Article {
		List<String> deletedCommentIdList;

		public List<String> getDeletedCommentIdList() {
			return deletedCommentIdList;
		}

		public void setDeletedCommentIdList(List<String> deletedCommentIdList) {
			this.deletedCommentIdList = deletedCommentIdList;
		}

	}

	@Override
	public void asserter(AssertManager assertManager) {
		assertManager.add("/leopardboot/RequestBody/uid").requestBody("{\"uid\":2}").name("验证uid为1").data(1);
		assertManager.add("/leopardboot/RequestBody/uid").name("uid默认为0").data(0);
		assertManager.add("/leopardboot/RequestBody/uid").name("uid默认为0").data(2);
		assertManager.add("/leopardboot/RequestBody/uid").requestBody("{\"uid\":3}").name("uid默认为0").data(3);
		// assertManager.add("/leopardboot/RequestBody/updateArticle").requestBody("{\"uid\":3}").name("uid默认为0").data(3);
	}

}
