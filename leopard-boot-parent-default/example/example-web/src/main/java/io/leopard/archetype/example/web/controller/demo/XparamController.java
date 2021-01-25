package io.leopard.archetype.example.web.controller.demo;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.boot.util.DateTime;

/**
 * 特殊参数
 * 
 * @author 谭海潮
 *
 */
@Controller
@RequestMapping("/xparam/")
public class XparamController {

	/**
	 * 获取时间
	 * 
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public String get(long sessUid, Date time) {
		return DateTime.getTime(time);
	}
}
