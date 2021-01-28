package io.leopard.boot.elasticsearch.model;

import java.util.LinkedHashMap;

public class Field extends LinkedHashMap<String, Object> {
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
