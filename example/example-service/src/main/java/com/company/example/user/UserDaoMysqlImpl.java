package com.company.example.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.leopard.jdbc.Jdbc;

@Repository
public class UserDaoMysqlImpl implements UserDao {

	@Autowired
	private Jdbc jdbc;

	@Override
	public boolean add(User user) {
		return false;
	}

	@Override
	public User get(Long uid) {
		String sql = "select * from user where uid=?;";
		return jdbc.query(sql, User.class, uid);
	}

	@Override
	public boolean delete(Long uid, long opuid, Date lmodify) {
		String sql = "delete from user where uid=?;";
		return jdbc.updateForBoolean(sql, uid);
	}

}
