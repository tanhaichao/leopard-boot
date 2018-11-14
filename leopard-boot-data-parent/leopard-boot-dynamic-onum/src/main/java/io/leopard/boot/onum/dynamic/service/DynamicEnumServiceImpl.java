package io.leopard.boot.onum.dynamic.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantEntity;
import io.leopard.boot.onum.dynamic.model.DynamicEnumDataVO;
import io.leopard.boot.onum.dynamic.model.DynamicEnumInfo;
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
		this.rsyncAll();
	}

	@Override
	public boolean rsyncAll() {
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
	public boolean rsync(String enumId) {
		if (StringUtils.isEmpty(enumId)) {
			throw new IllegalArgumentException("枚举ID不能为空.");
		}
		DynamicEnumInfo enumInfo = DynamicEnumManager.findDynamicEnumInfo(enumId);
		if (enumInfo == null) {// TODO 需要做这个兼容吗?
			return false;
		}
		List<DynamicEnumConstantEntity> recordList = this.dynamicEnumDao.list(enumId);

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
		List<DynamicEnumDataVO.EnumIO> enumVOList = new ArrayList<DynamicEnumDataVO.EnumIO>();
		sysconfigVO.setEnumList(enumVOList);
		for (DynamicEnumInfo enumInfo : DynamicEnumManager.getEnumList()) {
			String enumId = enumInfo.getEnumId();
			String className = enumInfo.getBeanClassName();
			List<DynamicEnumDataVO.EnumConstantIO> constantVOList = DynamicEnumManager.listByClassName(className);

			DynamicEnumDataVO.EnumIO enumVO = new DynamicEnumDataVO.EnumIO();
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
		if (StringUtils.isEmpty(enumId)) {
			throw new IllegalArgumentException("枚举ID不能为空.");
		}

		List<DynamicEnumConstantEntity> recordList = this.dynamicEnumDao.list(enumId);
		return toEnumConstantList(recordList, enumType);
	}

	public List<EnumConstant> toEnumConstantList(List<DynamicEnumConstantEntity> recordList, Class<?> enumType) {
		List<EnumConstant> constantList = new ArrayList<>();
		if (recordList != null) {
			for (DynamicEnumConstantEntity record : recordList) {
				if (record.isDisable()) {
					continue;
				}
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
	public boolean add(DynamicEnumConstantEntity entity, Operator operator) {
		if (StringUtils.isEmpty(entity.getEnumId())) {
			throw new IllegalArgumentException("枚举ID不能为空.");
		}
		Date posttime = new Date();
		entity.setPosttime(posttime);
		entity.setLmodify(posttime);
		boolean success = dynamicEnumDao.add(entity, operator);
		return success;
	}

	@Override
	public boolean delete(String enumId, String key, Operator operator) {
		if (StringUtils.isEmpty(enumId)) {
			throw new IllegalArgumentException("枚举ID不能为空.");
		}
		if (StringUtils.isEmpty(key)) {
			throw new IllegalArgumentException("枚举元素key不能为空.");
		}
		return dynamicEnumDao.delete(enumId, key, operator);
	}

	@Override
	public boolean update(DynamicEnumConstantEntity entity, Operator operator) {
		entity.setLmodify(new Date());
		boolean success = dynamicEnumDao.update(entity, operator);
		return success;
	}

	@Override
	public List<DynamicEnumConstantEntity> list(String enumId) {
		return dynamicEnumDao.list(enumId);
	}

	@Override
	public DynamicEnumConstantEntity get(String enumId, String key) {
		if (StringUtils.isEmpty(enumId)) {
			throw new IllegalArgumentException("枚举ID不能为空.");
		}
		if (StringUtils.isEmpty(key)) {
			throw new IllegalArgumentException("枚举元素key不能为空.");
		}
		return dynamicEnumDao.get(enumId, key);
	}
}
