package io.leopard.boot.kit.counter;

/**
 * 新数量计数器.
 * 
 * @author ahai
 *
 */
public interface NewCounterKit {

	long incr(String member, int num);

	long count(String member);

	boolean delete(String member);
}
