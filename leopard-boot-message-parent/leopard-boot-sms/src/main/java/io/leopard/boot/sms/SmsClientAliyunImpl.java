package io.leopard.boot.sms;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.HttpClientConfig;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.utils.StringUtils;

import io.leopard.json.Json;

@Component
@ConditionalOnProperty(prefix = "aliyun.sms", name = "accessKeyId")
public class SmsClientAliyunImpl implements SmsClient {

	protected Log logger = LogFactory.getLog(this.getClass());

	private IAcsClient client;

	@Value("${aliyun.sms.regionId:'cn-hangzhou'}")
	private String regionId;

	@Value("${aliyun.sms.accessKeyId}")
	private String accessKeyId;

	@Value("${aliyun.sms.secretAccessKey}")
	private String accessSecret;

	@Value("${aliyun.sms.signName}")
	private String signName;

	@Value("${leopard.proxy:}") // 默认为empty
	private String proxy;// 格式 ip:port

	@PostConstruct
	public void init() {
		DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessSecret);
		if (!StringUtils.isEmpty(proxy)) {
			HttpClientConfig clientConfig = HttpClientConfig.getDefault();
			String httpProxy = "http://" + proxy;
			clientConfig.setHttpProxy(httpProxy);
			clientConfig.setHttpsProxy(httpProxy);
			profile.setHttpClientConfig(clientConfig);
		}
		client = new DefaultAcsClient(profile);

		// PhoneNumbers String 是 15900000000
		// 接收短信的手机号码。
		//
		// 格式：
		//
		// 国内短信：11位手机号码，例如15951955195。
		// 国际/港澳台消息：国际区号+号码，例如85200000000。
		// 支持对多个手机号码发送短信，手机号码之间以英文逗号（,）分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。
		//
		// 说明 验证码类型短信，建议使用单独发送的方式。
		// SignName String 是 阿里云
		// 短信签名名称。请在控制台签名管理页面签名名称一列查看。
		//
		// 说明 必须是已添加、并通过审核的短信签名。
		// TemplateCode String 是 SMS_153055065
		// 短信模板ID。请在控制台模板管理页面模板CODE一列查看。
		//
		// 说明 必须是已添加、并通过审核的短信签名；且发送国际/港澳台消息时，请使用国际/港澳台短信模版。
		// AccessKeyId String 否 LTAIP00vvvvvvvvv
		// 主账号AccessKey的ID。
		//

		// TemplateParam String 否 {"code":"1111"}

	}

	@Override
	public boolean sendByTemplateId(String mobile, String templateId) {
		return this.sendByTemplateId(mobile, templateId, null);
	}

	@Override
	public boolean sendByTemplateId(String mobile, String templateId, boolean ignoreException) {
		if (ignoreException) {
			try {
				return this.sendByTemplateId(mobile, templateId, null);
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			}
		}
		else {
			return this.sendByTemplateId(mobile, templateId, null);
		}
	}

	@Override
	public boolean sendByTemplateId(String mobile, String templateId, Map<String, Object> data, boolean ignoreException) {
		if (ignoreException) {
			try {
				return this.sendByTemplateId(mobile, templateId, data);
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			}
		}
		else {
			return this.sendByTemplateId(mobile, templateId, data);
		}
	}

	@Override
	public boolean sendByTemplateId(String mobile, String templateId, Map<String, Object> data) {
		logger.info("sendByTemplateId mobile:" + mobile + " templateId:" + templateId);

		CommonRequest request = new CommonRequest();
		request.setSysMethod(MethodType.POST);
		request.setSysDomain("dysmsapi.aliyuncs.com");
		request.setSysVersion("2017-05-25");
		request.setSysAction("SendSms");
		request.putQueryParameter("RegionId", "cn-hangzhou");
		request.putQueryParameter("PhoneNumbers", mobile);
		request.putQueryParameter("SignName", signName);
		request.putQueryParameter("TemplateCode", templateId);

		if (data != null) {
			// String code = (String) data.get("code");
			// String templateParam = "{\"code\":\"" + code + "\"}";
			String templateParam = Json.toJson(data);
			request.putQueryParameter("TemplateParam", templateParam);
		}
		try {
			CommonResponse response = client.getCommonResponse(request);
			// {"Message":"OK","RequestId":"2AD6B746-C57C-4794-ACC4-62C943C2F3A4","BizId":"205500294640639892^0","Code":"OK"}
			String json = response.getData();
			if (json.indexOf("\"Code\":\"OK\"") == -1) {
				logger.error("json:" + json);
				throw new RuntimeException("短信接口出错.");
			}
			return true;
		}
		catch (ServerException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (ClientException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
