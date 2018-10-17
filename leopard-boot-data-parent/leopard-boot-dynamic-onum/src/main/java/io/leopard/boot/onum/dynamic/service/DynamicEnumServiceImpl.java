package io.leopard.boot.onum.dynamic.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leopard.boot.onum.dynamic.model.DynamicEnumDataVO;
import io.leopard.boot.onum.dynamic.model.DynamicEnumEntity;
import io.leopard.boot.onum.dynamic.model.DynamicEnumInfo;
import io.leopard.boot.onum.dynamic.model.DynamicEnumVO;
import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantVO;
import io.leopard.boot.onum.dynamic.model.Operator;
import io.leopard.lang.inum.dynamic.DynamicEnum;
import io.leopard.lang.inum.dynamic.EnumConstant;

@Service
public class DynamicEnumServiceImpl implements DynamicEnumService {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private DynamicEnumDao dynamicEnumDao;

	@PostConstruct
	public void init() {
		// new Exception().printStackTrace();
		this.rsync();
	}

	@Override
	public boolean rsync() {
		for (DynamicEnumInfo enumInfo : DynamicEnumManager.getEnumList()) {
			String enumId = enumInfo.getEnumId();
			String className = enumInfo.getBeanClassName();
			Class<?> type;
			try {
				type = Class.forName(className);
			}
			catch (ClassNotFoundException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			List<EnumConstant> constantList = resolve(enumId, type);
			// System.out.println("className:" + className);
			// Json.printList(constantList, "constantList");
			DynamicEnum.setEnumConstantList(className, constantList);
		}
		lmodify = new Date();
		return true;
	}

	@Override
	public boolean update(String enumId) {
		DynamicEnumInfo enumInfo = DynamicEnumManager.findDynamicEnumInfo(enumId);
		if (enumInfo == null) {// TODO 需要做这个兼容吗?
			return false;
		}
		List<DynamicEnumEntity> recordList = this.dynamicEnumDao.list(enumId);

		String className = enumInfo.getBeanClassName();
		Class<?> enumType = enumInfo.getEnumType();
		List<EnumConstant> constantList = this.toEnumConstantList(recordList, enumType);
		// System.out.println("className:" + className);
		// Json.printList(constantList, "constantList");
		DynamicEnum.setEnumConstantList(className, constantList);
		return true;
	}

	// private static List<DynamicEnumInfo> enumList = new ArrayList<DynamicEnumInfo>();

	private Date lmodify;

	@Override
	public DynamicEnumDataVO get() {
		DynamicEnumDataVO sysconfigVO = new DynamicEnumDataVO();
		List<DynamicEnumVO> enumVOList = new ArrayList<DynamicEnumVO>();
		sysconfigVO.setEnumList(enumVOList);
		for (DynamicEnumInfo enumInfo : DynamicEnumManager.getEnumList()) {
			String enumId = enumInfo.getEnumId();
			String className = enumInfo.getBeanClassName();
			List<DynamicEnumConstantVO> constantVOList = DynamicEnumManager.listByClassName(className);

			DynamicEnumVO enumVO = new DynamicEnumVO();
			enumVO.setEnumId(enumId);
			enumVO.setConstantList(constantVOList);
			enumVOList.add(enumVO);
		}
		sysconfigVO.setLmodify(lmodify);
		return sysconfigVO;
	}

	// public static void setEnumList(List<DynamicEnumInfo> enumList) {
	// DynamicEnumServiceImpl.enumList = enumList;
	// }

	@Override
	public List<EnumConstant> resolve(String enumId, Class<?> enumType) {
		List<DynamicEnumEntity> recordList = this.dynamicEnumDao.list(enumId);
		return toEnumConstantList(recordList, enumType);
	}

	public List<EnumConstant> toEnumConstantList(List<DynamicEnumEntity> recordList, Class<?> enumType) {
		List<EnumConstant> constantList = new ArrayList<>();
		if (recordList != null) {
			for (DynamicEnumEntity record : recordList) {
				// TODO 扩展参数未支持
				Object key = DynamicEnumManager.toObjectKey(enumType, record.getKey());
				EnumConstant constant = new EnumConstant();
				constant.setKey(key);
				constant.setDesc(record.getDesc());
				constantList.add(constant);
			}
		}
		return constantList;
	}

	@Override
	public boolean add(DynamicEnumEntity record, Operator operator) {
		boolean success = dynamicEnumDao.add(record, operator);
		String enumId = record.getEnumId();
		this.update(enumId);
		return success;
	}

	@Override
	public boolean delete(String enumId, String key, Operator operator) {
		boolean success = dynamicEnumDao.delete(enumId, key, operator);
		if (success) {
			this.update(enumId);
		}
		return success;
	}

	@Override
	public boolean update(DynamicEnumEntity record, Operator operator) {
		boolean success = dynamicEnumDao.update(record, operator);
		String enumId = record.getEnumId();
		this.update(enumId);
		return success;
	}

	@Override
	public List<DynamicEnumEntity> list(String enumId) {
		return dynamicEnumDao.list(enumId);
	}
}
