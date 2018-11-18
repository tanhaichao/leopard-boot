package io.leopard.boot.operator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;

/**
 * 更新操作人拦截器
 * 
 * @author 谭海潮
 *
 */
// @Aspect
// @Component
public class OperatorAspect {

	//TODO 测试，暂时写死
	@Before("execution(* (cn.nineox.zhaotong.scp..*Service).add(..))")
	public void add(JoinPoint joinPoint) {
		EntityInfo entityInfo = new EntityInfo(joinPoint);
		if (entityInfo.isNotEntityParameter()) {
			return;
		}
		long userId = 2;
		entityInfo.setFieldValue("createUserId", userId);
		entityInfo.setFieldValue("lastUpdateUserId", userId);

	}

	@Before("execution(* (cn.nineox.zhaotong.scp..*Service).update(..))")
	public void update(JoinPoint joinPoint) {
		EntityInfo entityInfo = new EntityInfo(joinPoint);
		if (entityInfo.isNotEntityParameter()) {
			return;
		}
		long userId = 2;
		entityInfo.setFieldValue("lastUpdateUserId", userId);
	}

	// public void before(JoinPoint joinPoint) {
	// MethodSignature signature = (MethodSignature) joinPoint.getSignature();
	// Method method = signature.getMethod();
	// System.out.println("方法规则式拦截," + method.getName());
	// }
}
