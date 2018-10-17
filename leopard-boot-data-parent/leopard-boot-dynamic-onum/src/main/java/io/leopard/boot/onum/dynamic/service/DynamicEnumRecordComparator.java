package io.leopard.boot.onum.dynamic.service;

import java.util.Comparator;

import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantEntity;

public class DynamicEnumRecordComparator implements Comparator<DynamicEnumConstantEntity> {

	@Override
	public int compare(DynamicEnumConstantEntity o1, DynamicEnumConstantEntity o2) {
		return o1.getPosition() - o2.getPosition();
	}

}
