package io.leopard.archetype.example.user;

import java.util.Date;

import io.leopard.data4j.cache.api.uid.IDelete;

/**
 * 用户
 * 
 * @author 谭海潮
 *
 */
public interface UserDao extends IDelete<User, Long> {
	/**
	 * 添加用户
	 */
	@Override
	boolean add(User user);

	/**
	 * 根据主键查询用户
	 */
	@Override
	User get(Long uid);

	/**
	 * 根据主键删除用户
	 */
	@Override
	boolean delete(Long uid, long opuid, Date lmodify);
}
