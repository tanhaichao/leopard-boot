package io.leopard.boot.pay.alipay.bankcard;

/**
 * 银行卡信息
 * 
 * @author 谭海潮
 *
 */
public class BankcardInfo {
	// https://blog.csdn.net/qq_28268507/article/details/68941754 根据银行卡号码获取银行卡归属行以及logo图标
	// https://blog.csdn.net/qq_21259459/article/details/99674378 判断银行卡号归属银行和卡类型
	// https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?cardNo=6222005865412565805&cardBinCheck=true
	// {"cardType":"DC","bank":"ICBC","key":"6222005865412565805","messages":[],"validated":true,"stat":"ok"}
	// {"messages":[{"errorCodes":"CARD_BIN_NOT_MATCH","name":"cardNo"}],"validated":false,"stat":"ok","key":"6220058655650"}

	/**
	 * 卡类型。值：DC为储蓄卡，CC为信用卡。
	 */
	private BankcardType cardType;

	/**
	 * 所属行。值：所属行简称，CMB 为招商银行
	 */
	private String bankCode;

	/**
	 * 银行卡号
	 */
	private String cardNo;

	/**
	 * 银行卡状态。值：ok，no。
	 */
	private BankcardStatus status;

	public BankcardType getCardType() {
		return cardType;
	}

	public void setCardType(BankcardType cardType) {
		this.cardType = cardType;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public BankcardStatus getStatus() {
		return status;
	}

	public void setStatus(BankcardStatus status) {
		this.status = status;
	}

}
