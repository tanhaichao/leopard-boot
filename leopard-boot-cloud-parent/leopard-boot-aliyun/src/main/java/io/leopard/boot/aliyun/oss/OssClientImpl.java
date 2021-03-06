package io.leopard.boot.aliyun.oss;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

@Service
@ConditionalOnProperty(prefix = "aliyun.oss", name = "endpoint")
// @Order(Ordered.HIGHEST_PRECEDENCE)
@Primary
public class OssClientImpl extends AbstractOssClient {

	// private static final String endpoint = "http://oss.aliyuncs.com";
	// private static final String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
	// private static final String endpoint = "http://oss-cn-shanghai.aliyuncs.com";

	@Value("${aliyun.oss.endpoint}")
	private String endpoint;

	@Value("${aliyun.oss.bucketName}")
	private String bucketName;

	@Value("${aliyun.oss.accessKeyId}")
	private String accessKeyId;

	@Value("${aliyun.oss.secretAccessKey}")
	private String secretAccessKey;

	/**
	 * 访问域名
	 */
	@Value("${leopard.upload.domain}")
	private String uploadServerDomain;

	/**
	 * 根目录，默认为/
	 */
	@Value("${aliyun.oss.rootDirectory:/}")
	private String rootDirectory;

	/**
	 * 目录hash长度
	 */
	@Value("${aliyun.oss.directoryHashLength:0}")
	private int directoryHashLength;

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public void setSecretAccessKey(String secretAccessKey) {
		this.secretAccessKey = secretAccessKey;
	}

	@PostConstruct
	public void init() {
		if (!rootDirectory.startsWith("/")) {
			throw new IllegalArgumentException("aliyun.oss.rootDirectory必须以/开始");
		}
		// System.out.println("bucketName:" + bucketName);
		// System.out.println("accessKeyId:" + accessKeyId);
		// System.out.println("secretAccessKey:" + secretAccessKey);
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

		OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, secretAccessKey);

		// OSSClient client = new OSSClient(endpoint, accessKeyId, secretAccessKey);
		// System.err.println("endpoint:" + endpoint);
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentLength(length);
		if (contentType != null) {
			meta.setContentType(contentType);
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

		PutObjectResult result = oss.putObject(bucketName, key, input, meta);
		logger.info(result.getETag());
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

		OSSClient client = new OSSClient(endpoint, accessKeyId, secretAccessKey);
		client.deleteObject(bucketName, key);
		return true;
	}

	@Override
	public boolean delete(String bucketName, String uri) {
		String key = uri.replaceFirst("^/", "");

		OSSClient client = new OSSClient(endpoint, accessKeyId, secretAccessKey);
		client.deleteObject(bucketName, key);
		return true;
	}

	@Override
	public boolean deleteDirectory(String bucketName, String directory) {
		return deleteByPrefix(bucketName, directory);
	}

	@Override
	public boolean deleteByPrefix(String bucketName, String prefix) {
		OSSClient client = new OSSClient(endpoint, accessKeyId, secretAccessKey);
		List<String> keys = this.getAllKey(client, bucketName, prefix);
		for (String key : keys) {
			System.err.println("deleteByPrefix key:" + key);
			client.deleteObject(bucketName, key);
		}
		return true;
	}

	/**
	 * 列出目录下所有文件(object)
	 * 
	 * @param prefix 指定文件夹
	 * @return
	 */
	public List<String> getAllKey(OSSClient client, String bucketName, String prefix) {
		List<String> commonPrefixes = getCommonPrefixes(client, bucketName, prefix);
		if (prefix.endsWith("/")) {
			commonPrefixes.add(prefix);
		}
		// 构造ListObjectsRequest请求
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
		List<String> list = new ArrayList<>();
		for (String string : commonPrefixes) {
			// System.err.println("prefix:" + prefix);
			// 递归列出fun目录下的所有文件
			listObjectsRequest.setPrefix(string);
			ObjectListing listing = client.listObjects(listObjectsRequest);
			for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
				list.add(objectSummary.getKey());
			}
		}
		return list;
	}

	/**
	 * 列出目录下所有子目录
	 * 
	 * @param prefix 指定文件夹
	 * @return
	 */
	protected List<String> getCommonPrefixes(OSSClient client, String bucketName, String prefix) {
		// 构造ListObjectsRequest请求
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
		// "/" 为文件夹的分隔符
		listObjectsRequest.setDelimiter("/");
		// 递归列出fun目录下的所有文件
		listObjectsRequest.setPrefix(prefix);
		ObjectListing listing = client.listObjects(listObjectsRequest);
		return listing.getCommonPrefixes();
	}
}
