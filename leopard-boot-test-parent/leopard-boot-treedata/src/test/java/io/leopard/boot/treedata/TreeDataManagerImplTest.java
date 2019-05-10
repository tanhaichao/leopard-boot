package io.leopard.boot.treedata;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import io.leopard.boot.treedata.Children;
import io.leopard.boot.treedata.TreeDataManagerImpl;
import io.leopard.json.Json;

public class TreeDataManagerImplTest {

	private TreeDataManagerImpl treeDataManager = new TreeDataManagerImpl();

	@Test
	public void parse() throws IOException {
		List<Children> list = treeDataManager.parse("/wheel.txt");
		Json.printList(list, "list");
	}

}