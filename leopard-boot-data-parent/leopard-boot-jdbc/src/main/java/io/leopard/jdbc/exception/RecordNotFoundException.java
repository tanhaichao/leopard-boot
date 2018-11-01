package io.leopard.jdbc.exception;

import org.springframework.dao.DataAccessException;

/**
 * 记录找不到
 * 
 * @author 谭海潮
 *
 */
public class RecordNotFoundException extends DataAccessException {

	private static final long serialVersionUID = 1L;

	public RecordNotFoundException(String msg) {
		super(msg);
	}

}
