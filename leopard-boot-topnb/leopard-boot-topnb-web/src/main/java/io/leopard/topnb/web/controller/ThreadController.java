package io.leopard.topnb.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.leopard.boot.basicauth.BasicAuth;
import io.leopard.topnb.TopnbBeanFactory;
import io.leopard.topnb.thread.ThreadInfo;
import io.leopard.topnb.thread.ThreadService;

/**
 * 性能监控数据
 * 
 * @author 阿海
 */
// @WebServlet(name = "topnbThreadServlet", urlPatterns = "/topnb/thread.leo")
@Controller("topnbThreadController")
@RequestMapping("/topnb/")
public class ThreadController {

	private static ThreadService threadService = TopnbBeanFactory.getThreadService();

	@RequestMapping("thread.leo")
	@BasicAuth
	public ModelAndView thread() {
		List<ThreadInfo> threadInfoList = threadService.listAll();
		ModelAndView view = new ModelAndView("/topnb/thread");

		view.addObject("threadInfoList", threadInfoList);
		// view.render(request, response);
		return view;
	}

}
