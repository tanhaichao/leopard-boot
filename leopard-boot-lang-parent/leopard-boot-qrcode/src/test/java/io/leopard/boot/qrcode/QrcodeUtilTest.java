package io.leopard.boot.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

public class QrcodeUtilTest {

	@Test
	public void encode() throws WriterException, IOException {
		String text = "http://www.baidu.com"; // 这里设置自定义网站url
		File logoPath = new File("C:\\Users\\Administrator\\Desktop\\logo.png");
		File destPath = new File("C:\\Users\\Administrator\\Desktop\\qrcode2.jpg");
		Logo logo = new Logo();
		logo.setFile(logoPath);

		QrcodeUtil.encode(text, 300, logo, destPath);
	}

	@Test
	public void decode() throws WriterException, IOException, NotFoundException {
		BufferedImage image = QrcodeUtil.createImage("baidu", 300);
		String content = QrcodeUtil.decode(image);
		System.out.println("content:" + content);
	}

}