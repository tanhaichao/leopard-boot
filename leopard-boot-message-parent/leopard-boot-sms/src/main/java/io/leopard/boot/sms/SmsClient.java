package io.leopard.boot.sms;

import java.util.Map;

public interface SmsClient {

	/**
	 * 通过模板ID发送短信
	 * 
	 * @param mobile 手机号码
	 * @param templateId 模板ID
	 * @param data 数据
	 * @return
	 */
	boolean sendByTemplateId(String mobile, String templateId, Map<String, Object> data);

	boolean sendByTemplateId(String mobile, String templateId);

	boolean sendByTemplateId(String mobile, String templateId, boolean ignoreException);

	boolean sendByTemplateId(String mobile, String templateId, Map<String, Object> data, boolean ignoreException);
}
