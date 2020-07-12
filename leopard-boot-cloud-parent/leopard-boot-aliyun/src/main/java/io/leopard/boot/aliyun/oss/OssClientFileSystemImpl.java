package io.leopard.boot.aliyun.oss;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;;

/**
 * 文件系统实现
 * 
 * @author 谭海潮
 *
 */
@Service
@ConditionalOnProperty(prefix = "aliyun.oss", name = "endpoint", matchIfMissing = true)
// @ConditionalOnMissingBean(value = OssClient.class)
// @ConditionalOnMissingBean(type = "io.leopard.boot.aliyun.oss.OssClient")
// @Order(Ordered.LOWEST_PRECEDENCE)
public class OssClientFileSystemImpl extends AbstractOssClient {

	/**
	 * 访问域名
	 */
	@Value("${leopard.upload.domain}")
	private String uploadServerDomain;

	/**
	 * 根目录，默认为/
	 */
	@Value("${leopard.upload.dir}")
	private String uploadDirectory;

	/**
	 * 目录hash长度
	 */
	private int directoryHashLength = 0;

	@PostConstruct
	public void init() {
		// if (!rootDirectory.startsWith("/")) {
		// throw new IllegalArgumentException("aliyun.oss.rootDirectory必须以/开始");
		// }
	}

	/**
	 * 目录hash
	 * 
	 * @param filename
	 * @return
	 */
	protected String directoryHash(String filename) {
		if (directoryHashLength <= 0) {
			return filename;
		}
		int index = filename.lastIndexOf(".");
		String name = filename.substring(0, index);
		if (name.length() <= directoryHashLength) {
			return filename;
		}
		String subdirectory = name.substring(0, directoryHashLength);
		return subdirectory + "/" + name + filename.substring(index);
	}

	@Override
	public String add(InputStream input, String dir, String filename, long length, String contentType) throws IOException {
		if (input == null) {
			throw new NullPointerException("input不能为空.");
		}

		filename = toUuidFileName(filename);
		filename = directoryHash(filename);// 目录hash

		String uri;
		if (dir == null || dir.length() == 0) {
			uri = filename;
		}
		else {
			uri = dir + "/" + filename;
		}

		logger.info("uri:" + uri + " lenght:" + length);

		// uploadDirectory

		File file = new File(this.uploadDirectory, uri);
		byte[] data = IOUtils.toByteArray(input);
		input.close();
		FileUtils.writeByteArrayToFile(file, data);

		// return "/" + uri;
		StringBuilder sb = new StringBuilder(uploadServerDomain);
		if (!uploadServerDomain.endsWith("/")) {
			sb.append("/");
		}
		sb.append(uri.replace("//", "/"));
		return sb.toString();
	}

	@Override
	public boolean move(String uri, String destUri) {
		throw new RuntimeException("not impl.");
	}

	@Override
	public boolean delete(String uri) {
		String key = uri.replaceFirst("^/", "");

		return true;
	}

	@Override
	public boolean delete(String bucketName, String uri) {
		String key = uri.replaceFirst("^/", "");

		return true;
	}

	@Override
	public boolean deleteDirectory(String bucketName, String directory) {
		return deleteByPrefix(bucketName, directory);
	}

	@Override
	public boolean deleteByPrefix(String bucketName, String prefix) {

		return true;
	}

}
