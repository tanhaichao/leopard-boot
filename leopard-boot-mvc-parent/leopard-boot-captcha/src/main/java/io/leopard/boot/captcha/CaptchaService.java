package io.leopard.boot.captcha;

import io.leopard.core.exception.forbidden.CaptchaWrongException;

public interface CaptchaService {

	boolean updateUsed(String captchaId, boolean used);

	Captcha check(String account, CaptchaType type, String target, String captcha) throws CaptchaWrongException;

	/**
	 * 发送验证码
	 * 
	 * @param account 账号，如手机号码、邮箱地址
	 * @param type 类型:mobile:手机，email:邮件
	 * @param target 目标，即在哪里使用
	 * @param content 消息模板
	 * @return
	 */
	String send(String account, CaptchaType type, String target, String content) throws FrequencyException;

	String send(String account, CaptchaType type, String target) throws FrequencyException;

	boolean updateUsed(Captcha captcha);

	void checkSessCaptcha(String captcha, String sessCaptcha) throws CaptchaWrongException;

	String send(String account, CaptchaType type, String target, String content, String data) throws FrequencyException;

	String send(String account, CaptchaType type, String target, String content, String data, int timeoutMinute) throws FrequencyException;

}
