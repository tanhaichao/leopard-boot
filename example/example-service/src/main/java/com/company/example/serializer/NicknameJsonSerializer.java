package com.company.example.serializer;

import org.springframework.beans.factory.annotation.Autowired;

import com.company.example.serializer.NicknameJsonSerializer.UserIO;
import com.company.example.user.User;
import com.company.example.user.UserService;

import io.leopard.web.mvc.json.IdJsonSerializer;

/**
 * 用户昵称
 */
public class NicknameJsonSerializer extends IdJsonSerializer<Long, UserIO> {

	@Autowired
	private UserService userService;

	@Override
	public UserIO get(Long uid) {
		User user = userService.get(uid);
		if (user == null) {
			return null;
		}
		UserIO userIO = new UserIO();
		userIO.setUid(user.getUid());
		userIO.setNickname(user.getNickname());
		return userIO;
	}

	/**
	 * 用户
	 * 
	 * @author 谭海潮
	 */
	public class UserIO {

		/**
		 * 用户ID
		 */
		private long uid;

		/**
		 * 昵称
		 */
		private String nickname;

		public long getUid() {
			return uid;
		}

		public void setUid(long uid) {
			this.uid = uid;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

	}

}