package io.leopard.boot.captcha;

import java.util.Date;

public interface CaptchaDao {

	boolean add(Captcha captcha);

	Captcha last(String account, String type, String target);

	boolean updateUsed(String captchaId, boolean used);

	boolean updateLmodify(String captchaId, Date lmodify);
}
