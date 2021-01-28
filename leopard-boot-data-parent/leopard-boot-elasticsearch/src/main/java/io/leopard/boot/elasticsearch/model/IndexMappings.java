package io.leopard.boot.elasticsearch.model;

import java.util.LinkedHashMap;

public class IndexMappings extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public void addKeywordField(String name) {
		this.addField(new KeywordField(name));
	}

	public void addKeywordField(String name, int ignoreAbove) {
		this.addField(new KeywordField(name, ignoreAbove));
	}

	public void addTextField(String name) {
		this.addField(new TextField(name));
	}

	public void addField(Field field) {
		if (this.containsKey(field.getName())) {
			throw new RuntimeException("字段[" + field.getName() + "]已存在.");
		}
		this.put(field.getName(), field);
	}

	public JoinField addJoinField(String name) {
		JoinField field = new JoinField(name);
		this.addField(field);
		return field;
	}

	JoinField joinField;

	public void addField(JoinField field) {
		if (joinField != null) {
			throw new RuntimeException("join字段只能设置一次.");
		}
		if (this.containsKey(field.getName())) {
			throw new RuntimeException("字段[" + field.getName() + "]已存在.");
		}
		this.put(field.getName(), field);
		this.joinField = field;
	}

}
