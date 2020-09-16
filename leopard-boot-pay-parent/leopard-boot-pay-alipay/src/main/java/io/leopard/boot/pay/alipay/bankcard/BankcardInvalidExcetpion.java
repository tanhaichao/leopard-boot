package io.leopard.boot.pay.alipay.bankcard;

/**
 * 无效的银行卡
 * 
 * @author 谭海潮
 *
 */
public class BankcardInvalidExcetpion extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BankcardInvalidExcetpion(String cardNo) {
		super("无效的银行卡号[" + cardNo + "].");
	}
}
