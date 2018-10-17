package io.leopard.fileupload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 * 本地文件系统实现
 * 
 * @author 谭海潮
 *
 */
public class UploadClientFileSystemImpl extends AbstractUploadClient {
	/**
	 * 访问域名
	 */
	@Value("${leopard.upload.domain}")
	private String uploadServerDomain;

	/**
	 * 文件上传目录
	 */
	@Value("${leopard.upload.dir}")
	private String uploadDir;

	@Override
	public String add(InputStream input, String dir, String filename, long lenght, String contentType) throws IOException {
		if (input == null) {
			throw new NullPointerException("input不能为空.");
		}
		filename = toUuidFileName(filename);
		String uri;
		if (dir == null || dir.length() == 0) {
			uri = filename;
		}
		else {
			uri = dir + "/" + filename;
		}
		System.out.println("uri:" + uri + " lenght:" + lenght);
		File file = new File(new File(uploadDir), uri);

		byte[] data = IOUtils.toByteArray(input);
		FileUtils.writeByteArrayToFile(file, data);

		uri = uri.replace("//", "/");
		return uploadServerDomain + "/" + uri;
	}

	@Override
	public boolean move(String uri, String destUri) {
		throw new RuntimeException("not impl.");
	}

}
