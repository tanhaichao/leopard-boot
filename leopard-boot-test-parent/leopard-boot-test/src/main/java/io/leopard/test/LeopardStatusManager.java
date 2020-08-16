package io.leopard.test;

import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.StatusListener;
import ch.qos.logback.core.status.StatusManager;

/**
 * 去掉logback状态日志
 * 
 * @author 谭海潮
 *
 */
public class LeopardStatusManager implements StatusManager {

	@Override
	public void add(Status status) {

	}

	@Override
	public List<Status> getCopyOfStatusList() {
		return new ArrayList<>();
	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public boolean add(StatusListener listener) {
		return false;
	}

	@Override
	public void remove(StatusListener listener) {

	}

	@Override
	public void clear() {

	}

	@Override
	public List<StatusListener> getCopyOfStatusListenerList() {
		return new ArrayList<>();
	}

}
