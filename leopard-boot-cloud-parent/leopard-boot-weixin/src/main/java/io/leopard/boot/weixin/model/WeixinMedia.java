package io.leopard.boot.weixin.model;

/**
 * 微信素材
 * 
 * @author 谭海潮
 *
 */
public class WeixinMedia {

	private String type;
	private String media_id;
	private int created_at;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public int getCreated_at() {
		return created_at;
	}

	public void setCreated_at(int created_at) {
		this.created_at = created_at;
	}
}
