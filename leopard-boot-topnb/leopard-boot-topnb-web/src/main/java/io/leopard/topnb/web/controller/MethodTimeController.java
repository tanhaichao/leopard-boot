package io.leopard.topnb.web.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.leopard.boot.basicauth.BasicAuth;
import io.leopard.topnb.TopnbBeanFactory;
import io.leopard.topnb.methodtime.EntryService;
import io.leopard.topnb.methodtime.MethodDto;
import io.leopard.topnb.methodtime.MethodTimeComparator;
import io.leopard.topnb.methodtime.MethodTimeHandler;

/**
 * 性能监控数据
 * 
 * @author 阿海
 */
// @WebServlet(name = "topnbMethodTimeServlet", urlPatterns = "/topnb/index.leo")
@Controller("topnbMethodTimeController")
@RequestMapping("/topnb/")
public class MethodTimeController {

	// private static MethodTimeService performanceService = TopnbBeanFactory.getMethodTimeService();

	private static MethodTimeHandler performanceHandler = TopnbBeanFactory.getPerformanceHandler();

	private static EntryService entryService = TopnbBeanFactory.getEntryService();

	@RequestMapping("/index.leo")
	@BasicAuth
	public ModelAndView index(String entryName, String order) {
		// if (true) {
		// super.output(response, "test");
		// return;
		// }
		// String entryName = request.getParameter("entryName");
		// String order = request.getParameter("order");

		List<MethodDto> performanceVOList = performanceHandler.list(entryName);
		List<String> entryNameList = entryService.listAllEntryName();
		String typeName = performanceHandler.getTypeName(entryName);

		Collections.sort(performanceVOList, MethodTimeComparator.get(order));
		ModelAndView view = new ModelAndView("/topnb/method_time");
		view.addObject("performanceVOList", performanceVOList);
		view.addObject("entryNameList", entryNameList);
		view.addObject("currentEntryName", entryName);
		view.addObject("typeName", typeName);
		return view;
	}

}
