package io.leopard.archetype.example.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.example.user.User;
import com.company.example.user.UserService;

import io.leopard.archetype.example.web.form.UserForm;
import io.leopard.archetype.example.web.vo.UserVO;
import io.leopard.lang.util.BeanUtil;

/**
 * 首页
 * 
 * @author 谭海潮
 *
 */
@Controller
@RequestMapping("/user/")
public class UserController {

	@Autowired
	private UserService userSerivce;

	/**
	 * 添加
	 * 
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public boolean add(UserForm form) {
		User user = BeanUtil.convert(form, User.class);
		userSerivce.add(user);
		return true;
	}

	/**
	 * 详情
	 * 
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public UserVO get(long uid) {
		User user = userSerivce.get(uid);
		UserVO userVO = BeanUtil.convert(user, UserVO.class);
		return userVO;
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public boolean delete(long uid) {
		userSerivce.delete(uid, 0);
		return true;
	}
}
