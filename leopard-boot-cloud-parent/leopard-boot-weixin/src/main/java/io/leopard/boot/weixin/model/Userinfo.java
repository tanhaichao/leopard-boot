package io.leopard.boot.weixin.model;

@Deprecated
class Userinfo {

	private String nickname;

	private String unionid;

	/**
	 * 用户关注的渠道来源
	 */
	private String subscribe_scene;
	// ADD_SCENE_SEARCH 公众号搜索，
	// ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，
	// ADD_SCENE_PROFILE_CARD 名片分享，
	// ADD_SCENE_QR_CODE 扫描二维码，
	// ADD_SCENE_PROFILE_LINK 图文页内名称点击，
	// ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，
	// ADD_SCENE_PAID 支付后关注，
	// ADD_SCENE_WECHAT_ADVERTISEMENT 微信广告，
	// ADD_SCENE_OTHERS 其他

	/**
	 * 二维码扫码场景
	 */
	private String qr_scene;

	/**
	 * 二维码扫码场景描述
	 */
	private String qr_scene_str;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getQr_scene() {
		return qr_scene;
	}

	public void setQr_scene(String qr_scene) {
		this.qr_scene = qr_scene;
	}

	public String getQr_scene_str() {
		return qr_scene_str;
	}

	public void setQr_scene_str(String qr_scene_str) {
		this.qr_scene_str = qr_scene_str;
	}

	public String getSubscribe_scene() {
		return subscribe_scene;
	}

	public void setSubscribe_scene(String subscribe_scene) {
		this.subscribe_scene = subscribe_scene;
	}

	// "subscribe": 1,
	// "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M",
	// "nickname": "Band",
	// "sex": 1,
	// "language": "zh_CN",
	// "city": "广州",
	// "province": "广东",
	// "country": "中国",
	// "headimgurl":"http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
	// "subscribe_time": 1382694957,
	// "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
	// "remark": "",
	// "groupid": 0,
	// "tagid_list":[128,2],
	// "subscribe_scene": "ADD_SCENE_QR_CODE",
	// "qr_scene": 98765,
	// "qr_scene_str": ""
}
