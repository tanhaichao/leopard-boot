package io.leopard.boot.captcha;

public class CaptchaDebugger {
	private static ThreadLocal<String> CAPTCHA = new ThreadLocal<String>();

	public static String getCaptcha() {
		return CAPTCHA.get();
	}

	public static void debug(String captcha, String content) {
		CAPTCHA.set(captcha);
		// JsonDebugger.addAttribute("captcha", content);
		// JsonDebugger.addAttribute("captcha_comment", "captcha属性是为了测试方便，只在测试环境返回。");
	}

}
