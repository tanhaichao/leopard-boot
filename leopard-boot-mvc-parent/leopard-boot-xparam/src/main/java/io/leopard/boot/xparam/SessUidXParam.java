package io.leopard.boot.xparam;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;

/**
 * 获取session中的uid.
 * 
 * @author 阿海
 * 
 */
@Service
public class SessUidXParam implements XParam {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public Object getValue(HttpServletRequest request, MethodParameter parameter) {
		// 分布式session还不够好，Long类型存进去再拿出来会变成Integer，这里做兼容
		Number sessUid = (Number) request.getSession().getAttribute("sessUid");
		// logger.info("getValue sessUid:" + sessUid);
		if (sessUid == null || sessUid.longValue() <= 0) {
			Nologin nologin = parameter.getMethodAnnotation(Nologin.class);
			if (nologin == null) {
				String ip = XParamUtil.getProxyIp(request);
				String uri = request.getRequestURI();
				throw new NotLoginException("您[ip:" + ip + " uri:" + uri + "]未登录.");
			}
			else {
				return 0L;
			}
		}
		return sessUid.longValue();
		// if (sessUid instanceof Integer) {
		// return new Long((Integer) sessUid);
		// }
		// if (sessUid instanceof Long) {
		// return sessUid;
		// }
		// throw new UnsupportedOperationException("未知类型[" + sessUid.getClass().getName() + "].");
	}

	public static void setSessUid(HttpServletRequest request, long uid) {
		request.getSession().setAttribute("sessUid", uid);
	}

	public static long getSessUid(HttpServletRequest request) {
		Number sessUid = (Number) request.getSession().getAttribute("sessUid");
		if (sessUid == null) {
			return 0L;
		}
		return sessUid.longValue();
	}

	@Override
	public String getKey() {
		return "sessUid";
	}
}
