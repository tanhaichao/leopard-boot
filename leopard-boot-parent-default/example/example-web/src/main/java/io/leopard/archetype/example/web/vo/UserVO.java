package io.leopard.archetype.example.web.vo;

import java.util.Date;

/**
 * 用户
 * 
 * @author 谭海潮
 *
 */
public class UserVO {

	/**
	 * 用户ID
	 */
	private long uid;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 添加时间
	 */
	private Date posttime;

	/**
	 * 最后修改时间
	 */
	private Date lmodify;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getPosttime() {
		return posttime;
	}

	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}

	public Date getLmodify() {
		return lmodify;
	}

	public void setLmodify(Date lmodify) {
		this.lmodify = lmodify;
	}

}
