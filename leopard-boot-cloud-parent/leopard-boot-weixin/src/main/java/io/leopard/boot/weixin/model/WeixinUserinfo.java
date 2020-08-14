package io.leopard.boot.weixin.model;

/**
 * 微信用户信息
 * 
 * @author 谭海潮
 *
 */
public class WeixinUserinfo {
	private String openId;

	private String nickName;

	private int gender;

	private String language;// zh_CN

	private String city;// Zhuhai

	private String province;// Guangdong

	private String country;// China

	private String avatarUrl;// https://wx.qlogo.cn/mmopen/vi_32/xxxxxxxxxxxxxxxxxxxxxxxxxxxxx/132

	private String unionId;

	private Watermark watermark;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public Watermark getWatermark() {
		return watermark;
	}

	public void setWatermark(Watermark watermark) {
		this.watermark = watermark;
	}

	public static class Watermark {

		private int timestamp;

		private String appid;
	}
}
