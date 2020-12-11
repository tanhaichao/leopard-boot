package io.leopard.spring;

import io.leopard.lang.inum.Snum;

/**
 * 服务器环境
 * 
 * @author 谭海潮
 *
 */
public enum ServerEnv implements Snum {
	DEV("dev", "开发环境"), TEST("test", "测试环境"), PRE("pre", "预发布环境"), PROD("prod", "生产环境");

	private String key;
	private String desc;

	private ServerEnv(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getDesc() {
		return desc;
	}

}
