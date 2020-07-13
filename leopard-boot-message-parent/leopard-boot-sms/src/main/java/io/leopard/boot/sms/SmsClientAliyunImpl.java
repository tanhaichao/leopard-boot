package io.leopard.boot.sms;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class SmsClientAliyunImpl implements SmsClient {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public boolean sendByTemplateId(String mobile, String templateId, Map<String, Object> data) {
		logger.info("sendByTemplateId mobile:" + mobile + " templateId:" + templateId);
		return true;
	}

}
