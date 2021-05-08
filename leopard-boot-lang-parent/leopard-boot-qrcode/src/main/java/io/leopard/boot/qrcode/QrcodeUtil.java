package io.leopard.boot.qrcode;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码
 * 
 * @author 谭海潮
 *
 */
public class QrcodeUtil {

	private static final String CHARSET = "utf-8";

	private static final String FORMAT_NAME = "JPG";

	public static BufferedImage createImage(String content, int qrcodeSize) throws WriterException, IOException {
		return createImage(content, qrcodeSize, null);
	}

	public static BufferedImage createImage(String content, int qrcodeSize, Logo logo) throws WriterException, IOException {
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrcodeSize, qrcodeSize, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		if (logo != null) {// 插入图片
			insertImage(image, qrcodeSize, logo);
		}
		return image;
	}

	/**
	 * 插入LOGO
	 *
	 * @param source 二维码图片
	 * @param qrcodeSize 二维码尺寸
	 * @param logo LOGO图片地址
	 * @throws IOException
	 */
	private static void insertImage(BufferedImage source, int qrcodeSize, Logo logo) throws IOException {
		File file = logo.getFile();
		if (!file.exists()) {
			throw new FileNotFoundException(file.getAbsolutePath());
		}
		Image src = ImageIO.read(file);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (logo.isNeedCompress()) { // 压缩LOGO
			if (width > logo.getWidth()) {
				width = logo.getWidth();
			}
			if (height > logo.getHeight()) {
				height = logo.getHeight();
			}
			Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (qrcodeSize - width) / 2;
		int y = (qrcodeSize - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 *
	 * @param content 内容
	 * @param qrcodeSize 二维码尺寸
	 * @param logo LOGO
	 * @param dest 存放目录
	 * @throws IOException
	 * @throws WriterException
	 */
	public static String encode(String content, int qrcodeSize, Logo logo, File dest) throws WriterException, IOException {
		BufferedImage image = createImage(content, qrcodeSize, logo);
		dest.getParentFile().mkdirs();
		String file = new Random().nextInt(99999999) + ".jpg";
		ImageIO.write(image, FORMAT_NAME, dest);
		return file;
	}

	/**
	 * 生成二维码
	 *
	 * @param content 内容
	 * @param qrcodeSize 二维码尺寸
	 * @param dest 存储地址
	 * @throws IOException
	 * @throws WriterException
	 */
	public static void encode(String content, int qrcodeSize, File dest) throws WriterException, IOException {
		encode(content, qrcodeSize, null, dest);
	}

	/**
	 * 解析二维码
	 *
	 * @param file 二维码图片
	 * @return
	 * @throws IOException
	 * @throws NotFoundException
	 */
	public static String decode(File file) throws IOException, NotFoundException {
		BufferedImage image = ImageIO.read(file);
		return decode(image);
	}

	public static String decode(BufferedImage image) throws NotFoundException {
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result;
		Map<DecodeHintType, Object> hints = new HashMap<>();
		hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
		result = new MultiFormatReader().decode(bitmap, hints);
		String resultStr = result.getText();
		return resultStr;
	}

}
