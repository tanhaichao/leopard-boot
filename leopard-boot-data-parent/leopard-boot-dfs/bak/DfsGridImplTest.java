package io.leopard.boot.data.dfs;

import org.junit.Test;

import io.leopard.boot.data.dfs.DfsGridImpl;

public class DfsGridImplTest {

	@Test
	public void test() throws Exception {
		DfsGridImpl dfsGridImpl = new DfsGridImpl();
		dfsGridImpl.afterPropertiesSet();
		dfsGridImpl.read("/test.jpg");
		dfsGridImpl.destroy();
	}

}