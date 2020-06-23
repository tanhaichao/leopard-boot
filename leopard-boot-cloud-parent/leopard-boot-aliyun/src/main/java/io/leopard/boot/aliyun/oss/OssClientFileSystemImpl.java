package io.leopard.boot.aliyun.oss;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 文件系统实现
 * 
 * @author 谭海潮
 *
 */
@Service
@ConditionalOnProperty(prefix = "aliyun.oss", name = "endpoint", matchIfMissing = true)
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
	private String rootDirectory;

	/**
	 * 目录hash长度
	 */
	private int directoryHashLength = 0;

	@PostConstruct
	public void init() {
		if (!rootDirectory.startsWith("/")) {
			throw new IllegalArgumentException("aliyun.oss.rootDirectory必须以/开始");
		}
	}

	/**
	 * 加上rootDirectory;
	 * 
	 * @param uri
	 * @return
	 */
	protected String joinRootDirectory(String uri) {
		uri = this.rootDirectory + "/" + uri;
		uri = uri.replace("//", "/");
		uri = uri.replaceFirst("^/", "");
		return uri;
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
		uri = joinRootDirectory(uri);
		logger.info("uri:" + uri + " lenght:" + length);
		String key = uri.replaceFirst("^/", "");

		logger.info("key:" + key);
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
