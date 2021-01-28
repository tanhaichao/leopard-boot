package io.leopard.boot.elasticsearch.model;

public class TextField extends Field {

	private static final long serialVersionUID = 1L;

	public TextField(String name) {
		super(name);
		this.put("type", "text");
		this.put("analyzer", "whitespace");
		this.put("search_analyzer", "whitespace");
		this.put("fielddata", true);
	}

}
