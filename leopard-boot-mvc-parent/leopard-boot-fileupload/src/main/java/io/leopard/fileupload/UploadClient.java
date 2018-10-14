package io.leopard.fileupload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 * 
 * @author 谭海潮
 *
 */
public interface UploadClient {

	String add(String subDir, MultipartFile file) throws IOException;

	String add(String subDir, MultipartFile file, Set<String> extnameSet) throws IOException;

	String add(InputStream input, String dir, String filename, long lenght) throws IOException;

	String add(InputStream input, String dir, String filename, long lenght, String contentType) throws IOException;

	boolean move(String uri, String destUri);

	String toUuidFileName(String filename);

	void checkExtname(String filename, Set<String> extnameSet);

}
