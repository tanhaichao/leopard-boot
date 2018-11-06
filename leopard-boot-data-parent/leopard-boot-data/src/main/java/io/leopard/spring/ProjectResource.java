package io.leopard.spring;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StringUtils;

/**
 * 项目资源
 * 
 * @author 谭海潮
 *
 */
public class ProjectResource extends FileSystemResource {

	public ProjectResource(String path) throws FileNotFoundException {
		super(getFile(path));
	}

	protected static File getFile(String path) throws FileNotFoundException {
		String userDir = System.getProperty("user.dir");
		java.io.File webappRootDir = new File(userDir);
		// TODO 必须符合多模块开发规范
		java.io.File projectRootDir = webappRootDir.getParentFile();

		String pathToUse = StringUtils.cleanPath(path);
		return new File(projectRootDir, pathToUse);
	}

}
