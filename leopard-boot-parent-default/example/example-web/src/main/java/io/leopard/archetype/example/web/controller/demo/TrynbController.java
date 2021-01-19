package io.leopard.archetype.example.web.controller.demo;

import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.boot.trynb.annotations.Trynb;

/**
 * 异常测试
 * 
 * @author 谭海潮
 *
 */
@Controller
@RequestMapping("/trynb/")
public class TrynbController {

	/**
	 * 详情
	 * 
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	@Trynb(value = RuntimeException.class, message = "trynb-warn1")
	@Trynb(value = IllegalArgumentException.class, level = LogLevel.WARN, printStackTrace = false, message = "trynb3")
	@Trynb(NoSuchFieldException.class)
	@Trynb(NoSuchFieldException.class)
	@Trynb(NoSuchMethodException.class)
	// @TrynbWarn(RuntimeException.class)
	// @TrynbWarn(RuntimeException.class)
	// @TrynbWarn(RuntimeException.class)
	public String get(long sessUid, long uid) {
		if (uid == 0) {
			throw new IllegalArgumentException("用户ID不能为空.");
		}
		return "hello";
	}
}
