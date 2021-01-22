package io.leopard.boot.admin.accesslog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.leopard.boot.admin.accesslog.model.AccessLog;
import io.leopard.jdbc.Jdbc;

@Component
public class AdminAccessLogService {

	@Autowired
	private Jdbc jdbc;

	public void add(AccessLog accessLog) {
		jdbc.insert("admin_access_log", accessLog);
	}
}
