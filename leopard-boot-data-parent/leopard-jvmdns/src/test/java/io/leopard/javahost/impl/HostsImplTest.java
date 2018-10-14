package io.leopard.javahost.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import io.leopard.jvmdns.Host;
import io.leopard.jvmdns.impl.HostsImpl;

public class HostsImplTest {

	private HostsImpl hosts = new HostsImpl();

	@Test
	public void list() {
		List<Host> list = hosts.list();
		for (Host host : list) {
			System.out.println(host.toString());
		}
	}

	@Test
	public void query() {
		Assert.assertEquals("112.126.75.27", hosts.query("leopard2e.leopard.io"));
	}

	@Test
	public void exist() {
		Assert.assertTrue(hosts.exist("leopard2e.leopard.io"));
	}

}