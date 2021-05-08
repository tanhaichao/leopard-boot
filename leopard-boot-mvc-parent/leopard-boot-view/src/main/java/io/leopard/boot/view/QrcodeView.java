package io.leopard.boot.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import io.leopard.boot.qrcode.Logo;
import io.leopard.boot.qrcode.QrcodeUtil;

/**
 * 二维码
 * 
 * @author 阿海
 * 
 */
public class QrcodeView extends ModelAndView {

	/**
	 * 二维码尺寸
	 */
	private int qrcodeSize;

	private String content;

	private Logo logo;

	public QrcodeView(String content) {
		this(content, 300);
	}

	public QrcodeView(String content, int qrcodeSize) {
		super.setView(view);
		this.content = content;
		this.qrcodeSize = qrcodeSize;
	}

	public void setLogo(File logo) {
		this.logo = new Logo();
		this.logo.setFile(logo);
	}

	public void setLogo(File logo, int size) {
		this.logo = new Logo();
		this.logo.setFile(logo);
		this.logo.setHeight(size);
		this.logo.setWidth(size);
	}

	public String getContent() {
		return content;
	}

	private AbstractUrlBasedView view = new AbstractUrlBasedView() {
		@Override
		protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
			response.setContentType("image/png");
			BufferedImage image = QrcodeUtil.createImage(content, qrcodeSize, logo);

			// InputStream input = new ByteArrayInputStream(data);
			java.io.OutputStream out = response.getOutputStream();
			ImageIO.write(image, "png", out);
			// byte[] b = new byte[1024];
			// int i = 0;
			// while ((i = input.read(b)) > 0) {
			// out.write(b, 0, i);
			// }
			// input.close();
			out.flush();
			// output.close();
		}
	};
}