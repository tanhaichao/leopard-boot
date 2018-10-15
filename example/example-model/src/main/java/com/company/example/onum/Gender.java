package com.company.example.onum;

import io.leopard.lang.inum.Snum;

/**
 * 性别
 * 
 * @author 谭海潮
 */
public enum Gender implements Snum {
	MALE("male", "男")//
	, FEMALE("female", "女");

	private String key;

	private String desc;

	private Gender(String key, String desc) {
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