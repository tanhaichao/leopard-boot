package io.leopard.boot.weixin.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 微信公众号个人信息
 */
public class OffiaccountUserinfo {

	/**
	 * openId
	 */
	@JsonProperty("openid")
	private String openId;

	/**
	 * unionId
	 */
	@JsonProperty("unionid")
	private String unionId;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 性别
	 */
	private int sex;

	/**
	 * 城市
	 */
	private String city;

	/**
	 * 省份
	 */
	private String province;

	/**
	 * 国家
	 */
	private String country;

	/**
	 * 头像
	 */
	private String headimgurl;

	/**
	 * 订阅时间
	 */
	@JsonProperty("subscribe_time")
	private Date subscribeTime;

	/**
	 * 订阅场景
	 */
	@JsonProperty("subscribe_scene")
	private String subscribeScene;

	/**
	 * 二维码场景ID
	 */
	@JsonProperty("qr_scene")
	private long qrScene;

	/**
	 * 二维码场景描述
	 */
	@JsonProperty("qr_scene_str")
	private String qrSceneStr;

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUnionId() {
		return this.unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return this.sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return this.headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Date getSubscribeTime() {
		return this.subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public void setSubscribeTime(int subscribeTime) {
		this.subscribeTime = new Date(subscribeTime * 1000);
	}

	public String getSubscribeScene() {
		return this.subscribeScene;
	}

	public void setSubscribeScene(String subscribeScene) {
		this.subscribeScene = subscribeScene;
	}

	public long getQrScene() {
		return this.qrScene;
	}

	public void setQrScene(long qrScene) {
		this.qrScene = qrScene;
	}

	public String getQrSceneStr() {
		return this.qrSceneStr;
	}

	public void setQrSceneStr(String qrSceneStr) {
		this.qrSceneStr = qrSceneStr;
	}

}
