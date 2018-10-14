package io.leopard.boot.timer;

/**
 * 定时器.
 * 
 * @author 谭海潮
 * 
 */
public abstract class AbstractTimer implements Timer {
	// protected final Log logger = LogFactory.getLog("TIMERLOG." + this.getClass().getName());

	@Override
	public int getThreadCount() {
		return 1;
	}

}
