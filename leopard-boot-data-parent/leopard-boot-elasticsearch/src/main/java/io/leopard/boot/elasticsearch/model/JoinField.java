package io.leopard.boot.elasticsearch.model;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class JoinField extends Field {

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