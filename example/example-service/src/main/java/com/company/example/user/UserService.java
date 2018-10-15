package com.company.example.user;

/**
 * 用户
 * 
 * @author 谭海潮
 *
 */
public interface UserService {
	/**
	 * 添加用户
	 */

	boolean add(User user);

	/**
	 * 根据主键查询用户
	 */

	User get(long uid);

	/**
	 * 根据主键删除用户
	 */

	boolean delete(long uid, long opuid);
}
