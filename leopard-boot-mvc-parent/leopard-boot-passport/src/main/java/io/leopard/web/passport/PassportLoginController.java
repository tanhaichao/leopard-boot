package io.leopard.web.passport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 性能监控数据
 * 
 * @author 阿海
 */
// @WebServlet(name = "passportLoginServlet", urlPatterns = "/passport/login.leo")
@Controller
@RequestMapping("/passport/")
public class PassportLoginController {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private PassportValidatorFinder passportValidatorFinder;

	@RequestMapping("/login")
	public void login(String type, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (StringUtils.isEmpty(type)) {
			type = "sessUid";
		}
		boolean flag = passportValidatorFinder.find(type).login(request, response);
		logger.info("login type:" + type + " flag:" + flag);
		if (!flag) {
			throw new RuntimeException("未实现PassportValidate.login接口");
		}
	}

	// @Override
	// protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// try {
	// login(request, response);
	// }
	// catch (Exception e) {
	// String message = ErrorUtil.parseMessage(e);
	// logger.error(e.getMessage(), e);
	// this.output(response, message);
	// return;
	// }
	// }
	//
	//
	// private void output(HttpServletResponse response, String text) throws IOException {
	// byte[] bytes = text.getBytes();
	// response.setContentType("text/plain; charset=UTF-8");
	// response.setContentLength(bytes.length);
	// OutputStream out = response.getOutputStream();
	// out.write(bytes);
	// out.flush();
	// }

}
