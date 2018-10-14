package io.leopard.boot.responsebody;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 响应实体类(JSON结构)
 * 
 * @author 谭海潮
 *
 */
public class ResponseEntity {

	/**
	 * 状态码
	 */
	private String status;

	/**
	 * 错误消息
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;

	/**
	 * 数据
	 */
	// @JsonInclude(JsonInclude.Include.NON_NULL)
	private Object data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
