package io.leopard.boot.elasticsearch.model;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class IndexMappings extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public void init() {
		{
			Map<String, Object> mapping = new LinkedHashMap<>();
			Map<String, Object> properties = new LinkedHashMap<>();
			{
				Map<String, Object> nameProperty = new LinkedHashMap<>();
				nameProperty.put("type", "text");
				nameProperty.put("analyzer", "whitespace");
				nameProperty.put("search_analyzer", "whitespace");
				nameProperty.put("fielddata", true);
				properties.put("drugName", nameProperty);
			}
			{
				Map<String, Object> nameProperty = new LinkedHashMap<>();
				nameProperty.put("type", "text");
				nameProperty.put("analyzer", "whitespace");
				nameProperty.put("search_analyzer", "whitespace");
				nameProperty.put("fielddata", true);
				properties.put("packetName", nameProperty);
			}
			{
				Map<String, Object> nameProperty = new LinkedHashMap<>();
				nameProperty.put("type", "text");
				nameProperty.put("analyzer", "whitespace");
				nameProperty.put("search_analyzer", "whitespace");
				nameProperty.put("fielddata", true);
				properties.put("drugNames", nameProperty);
			}
			{
				Map<String, Object> nameProperty = new LinkedHashMap<>();
				nameProperty.put("type", "text");
				nameProperty.put("analyzer", "whitespace");
				nameProperty.put("search_analyzer", "whitespace");
				nameProperty.put("fielddata", true);
				properties.put("commodityId", nameProperty);
			}
			// {
			// Map<String, Object> nameProperty = new LinkedHashMap<>();
			// nameProperty.put("type", "text");
			// nameProperty.put("analyzer", "whitespace");
			// nameProperty.put("search_analyzer", "whitespace");
			// nameProperty.put("fielddata", true);
			// properties.put("warehouseId", nameProperty);
			// }

			{
				Map<String, Object> nameProperty = new LinkedHashMap<>();
				Map<String, Object> relations = new LinkedHashMap<>();
				nameProperty.put("type", "join");
				nameProperty.put("relations", relations);
				{
					relations.put("commodity", Arrays.asList("inventory", "sales_drug", "group_drug"));
					relations.put("packet", Arrays.asList("packet_inventory", "sales_packet"));
					relations.put("packet_drug", "packet_drug_child");// 没有使用？
					// {
					// relations.put("salesdrug", "sales_drug");
					// relations.put("salespacket", "sales_packet");
					// }
				}
				properties.put("inventoryList", nameProperty);
			}
		}
	}

	public void addField(Field field) {
		if (this.containsKey(field.getName())) {
			throw new RuntimeException("字段[" + field.getName() + "]已存在.");
		}
		this.put(field.getName(), field);
	}

	JoinField joinField;

	public void addField(JoinField joinField) {
		if (joinField != null) {
			throw new RuntimeException("join字段只能设置一次.");
		}
		this.addField(joinField);
		this.joinField = joinField;
	}

	public static class Field extends LinkedHashMap<String, Object> {
		private static final long serialVersionUID = 1L;

		/**
		 * 字段名称
		 */
		private final String name;

		public Field(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

	public static class JoinField extends Field {

		private static final long serialVersionUID = 1L;
		Map<String, Set<String>> relations = new LinkedHashMap<>();

		public JoinField(String name) {
			super(name);

			this.put("type", "join");
			this.put("relations", relations);
		}

		public void addChild(String parent, String child) {
			Set<String> _children = relations.get(parent);
			if (_children == null) {
				_children = new LinkedHashSet<>();
				relations.put(parent, _children);
			}
			_children.add(child);
		}

		public void addChildren(String parent, Set<String> children) {
			Set<String> _children = relations.get(parent);
			if (_children == null) {
				_children = new LinkedHashSet<>();
				relations.put(parent, _children);
			}
			_children.addAll(children);
		}
	}

}
