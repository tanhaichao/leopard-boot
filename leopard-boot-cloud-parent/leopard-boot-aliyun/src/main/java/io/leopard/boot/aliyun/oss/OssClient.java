package io.leopard.boot.aliyun.oss;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

/**
 * 阿里云OSS
 * 
 * @author 谭海潮
 *
 */
public interface OssClient {

	String add(String dir, MultipartFile file) throws IOException;

	String add(String dir, MultipartFile file, Set<String> extnameSet) throws IOException;

	String add(InputStream input, String dir, String filename, long length) throws IOException;

	String add(InputStream input, String dir, String filename, long length, String contentType) throws IOException;

	boolean move(String uri, String destUri);

	String toUuidFileName(String filename);

	void checkExtname(String filename, Set<String> extnameSet);

	boolean delete(String uri);

	boolean delete(String bucketName, String uri);

	boolean deleteDirectory(String bucketName, String directory);

	boolean deleteByPrefix(String bucketName, String prefix);
}
