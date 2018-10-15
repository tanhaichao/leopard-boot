package com.company.example.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public boolean add(User user) {

		return userDao.add(user);
	}

	@Override
	public User get(long uid) {
		return userDao.get(uid);
	}

	@Override
	public boolean delete(long uid, long opuid) {
		return userDao.delete(uid, opuid, new Date());
	}

}
