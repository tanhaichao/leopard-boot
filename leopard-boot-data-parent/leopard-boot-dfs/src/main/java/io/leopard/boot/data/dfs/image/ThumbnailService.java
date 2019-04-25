package io.leopard.boot.data.dfs.image;

import java.io.IOException;

/**
 * 缩略图.
 * 
 * @author 阿海
 *
 */
public interface ThumbnailService {
	byte[] read(String filename) throws IOException;

}
