package io.leopard.boot.onum.dynamic.service;

import java.util.Comparator;

import io.leopard.boot.onum.dynamic.model.DynamicEnumEntity;

public class DynamicEnumRecordComparator implements Comparator<DynamicEnumEntity> {

	@Override
	public int compare(DynamicEnumEntity o1, DynamicEnumEntity o2) {
		return o1.getPosition() - o2.getPosition();
	}

}
