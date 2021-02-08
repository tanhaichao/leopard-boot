package io.leopard.boot.view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

/**
 * HTML视图
 * 
 * @author 阿海
 * 
 */
public class DownloadView extends ModelAndView {

	private String fileName;
	private byte[] data;

	public DownloadView(String fileName, byte[] data) {
		super.setView(view);
		this.fileName = fileName;
		this.data = data;
	}

	private AbstractUrlBasedView view = new AbstractUrlBasedView() {

		@Override
		protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

			response.setContentType("application/force-download");
			String filedisplay = URLEncoder.encode(fileName, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);

			// ByteArrayOutputStream output = new ByteArrayOutputStream();

			InputStream input = new ByteArrayInputStream(data);
			java.io.OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = input.read(b)) > 0) {
				out.write(b, 0, i);
			}
			input.close();
			out.flush();
			// output.close();
		}
	};

}