package io.leopard.jdbc.traverse;

/**
 * 遍历器
 * 
 * @author 谭海潮
 *
 */
public interface Traverser<T> {

	void traverse(T bean);

}
