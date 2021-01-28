package io.leopard.boot.elasticsearch.model;

import org.junit.Test;

import io.leopard.json.Json;

public class IndexMappingsTest {

	@Test
	public void IndexMappings() {
		IndexMappings mappings = new IndexMappings();
		mappings.addTextField("drugName");
		// JoinField joinField = new JoinField("inventoryList");
		JoinField joinField = mappings.addJoinField("inventoryList");
		Json.printFormat(mappings, "mappings");
	}

}