package io.leopard.boot.elasticsearch;

import org.junit.Test;

public class MatchTest {

	@Test
	public void test() {
		double n = 999999;
		System.err.println(Math.log(n));
		System.err.println(Math.log10(n));
		System.err.println(Math.log10(n + 1));
		System.err.println(Math.log10(n + 2));
		System.err.println(Math.log1p(n));
		System.err.println(Math.log1p(n + 1));
		System.err.println(Math.log1p(n + 2));
		System.err.println(1.0 / n);
	}
}
