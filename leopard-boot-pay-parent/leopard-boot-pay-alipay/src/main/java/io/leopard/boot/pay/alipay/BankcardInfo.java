package io.leopard.boot.pay.alipay;

/**
 * 银行卡信息
 * 
 * @author 谭海潮
 *
 */
public class BankcardInfo {
	// https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?cardNo=6222005865412565805&cardBinCheck=true
	// {"cardType":"DC","bank":"ICBC","key":"6222005865412565805","messages":[],"validated":true,"stat":"ok"}
	// {"messages":[{"errorCodes":"CARD_BIN_NOT_MATCH","name":"cardNo"}],"validated":false,"stat":"ok","key":"6220058655650"}

	private String cardType;

	private String bank;

	private String key;

	private boolean validated;

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

}
