package io.leopard.boot.converter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.lang.datatype.Month;

@Controller
@RequestMapping("/leopard/converter/")
public class ConverterController {
	@RequestMapping("/month")
	@ResponseBody
	public Month month(Month month) {
		return month;
	}
}
