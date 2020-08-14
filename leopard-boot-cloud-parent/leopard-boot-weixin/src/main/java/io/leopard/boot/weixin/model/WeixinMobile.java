package io.leopard.boot.weixin.model;

/**
 * 微信用户信息
 * 
 * @author 谭海潮
 *
 */
public class WeixinMobile extends WeixinData {

	private String phoneNumber;

	private String purePhoneNumber;

	private String countryCode;// 86

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPurePhoneNumber() {
		return purePhoneNumber;
	}

	public void setPurePhoneNumber(String purePhoneNumber) {
		this.purePhoneNumber = purePhoneNumber;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

}
