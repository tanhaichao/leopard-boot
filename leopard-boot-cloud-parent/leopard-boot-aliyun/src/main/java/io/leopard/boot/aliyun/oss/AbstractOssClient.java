package io.leopard.boot.aliyun.oss;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public abstract class AbstractOssClient implements OssClient {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public String add(String dir, MultipartFile file) throws IOException {
		return this.add(dir, file, extnameSet);
	}

	@Override
	public String add(String dir, MultipartFile file, Set<String> extnameSet) throws IOException {
		if (file == null) {
			throw new NullPointerException("文件不能为空.");
		}
		String fileName = file.getOriginalFilename();
		if ("blob".equals(fileName)) {// 支持blob文件上传
			String contentType = file.getContentType();
			// TODO 类型未做限制，未完整支持
			if ("image/jpeg".equals(contentType)) {
				fileName = "blob.jpg";
			}
			else {
				throw new RuntimeException("未支持该文件类型解析[" + contentType + "].");
			}
		}
		checkExtname(fileName, extnameSet);
		return this.add(file.getInputStream(), dir, fileName, file.getSize());
	}

	@Override
	public String add(InputStream input, String dir, String filename, long length) throws IOException {
		return this.add(input, dir, filename, length, null);
	}

	/** 支持的图片后缀，支持大小写 */
	protected Set<String> extnameSet = new HashSet<String>();

	public AbstractOssClient() {
		extnameSet.add("jpg");
		extnameSet.add("png");
		extnameSet.add("gif");
		extnameSet.add("jpeg");
	}

	/**
	 * 检查文件扩展名
	 * 
	 * @param fileName
	 * @param picPrefix
	 * @return
	 */
	@Override
	public void checkExtname(String filename, Set<String> extnameSet) {
		if (StringUtils.isEmpty(filename)) {
			throw new IllegalArgumentException("文件名称不能为空.");
		}
		if (extnameSet == null || extnameSet.isEmpty()) {
			return;
		}
		String extname = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
		if (!extnameSet.contains(extname)) {
			throw new IllegalArgumentException("文件格式不合法[" + extname + "].");
		}
	}

	@Override
	public String toUuidFileName(String filename) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
		return uuid + filename.substring(filename.lastIndexOf("."));
	}
}
