package io.leopard.boot.elasticsearch.model;

/**
 * 关键字字段 不进行分词，直接索引 支持模糊、精确查询 支持聚合
 * 
 * @author 谭海潮
 *
 */
public class KeywordField extends Field {

	private static final long serialVersionUID = 1L;

	public KeywordField(String name) {
		this(name, 256);
	}

	public KeywordField(String name, int ignoreAbove) {
		super(name);
		this.put("type", "keyword");
		this.put("ignore_above", "ignoreAbove");
	}

}
