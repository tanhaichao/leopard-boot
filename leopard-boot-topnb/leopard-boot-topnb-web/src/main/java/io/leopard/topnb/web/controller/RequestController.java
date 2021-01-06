package io.leopard.topnb.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.leopard.boot.basicauth.BasicAuth;
import io.leopard.topnb.TopnbBeanFactory;
import io.leopard.topnb.request.RequestDto;
import io.leopard.topnb.request.RequestService;

/**
 * 请求耗时.
 * 
 * @author 阿海
 *
 */
// @WebServlet(name = "topnbRequestServlet", urlPatterns = "/topnb/request.leo")
@Controller("topnbRequestController")
@RequestMapping("/topnb/")
public class RequestController {

	private static RequestService requestService = TopnbBeanFactory.getRequestService();

	@RequestMapping("request.leo")
	@BasicAuth
	public ModelAndView request() {
		List<RequestDto> requestList = requestService.list();
		ModelAndView view = new ModelAndView("/topnb/request");

		view.addObject("requestList", requestList);
		// view.render(request, response);
		return view;
	}

}
