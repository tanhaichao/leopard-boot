package io.leopard.boot.translate;

/**
 * 翻译
 * 
 * @author 谭海潮
 *
 */
public interface Translater {

	boolean isEnable();

	String translate(String message);

}