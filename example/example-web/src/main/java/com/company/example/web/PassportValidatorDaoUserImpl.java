package com.company.example.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import io.leopard.web.passport.PassportGroup;
import io.leopard.web.passport.PassportValidator;

/**
 * 用户登录认证器
 * 
 * @author 谭海潮
 *
 */
@Component
@PassportGroup("sessUid")
public class PassportValidatorDaoUserImpl implements PassportValidator {

	protected Log logger = LogFactory.getLog(PassportValidatorDaoUserImpl.class);

	@Override
	public Object validate(HttpServletRequest request, HttpServletResponse response) {
		logger.info("validate xxxx...");
		// TODO 在这里实现用户登录认证
		return 1L;// 返回当前的认证通过的用户ID，
	}

	@Override
	public boolean login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return true;
	}

	@Override
	public boolean showLoginBox(HttpServletRequest request, HttpServletResponse response) {
		return false;
	}

	@Override
	public Boolean isNeedCheckLogin(HttpServletRequest request, Object handler) {
		return null;
	}

}
