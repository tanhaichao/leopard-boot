package io.leopard.boot.trynb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.leopard.boot.responsebody.ResponseEntity;
import io.leopard.json.Json;
import io.leopard.web.view.AbstractView;

/**
 * 普通文本.
 * 
 * @author 阿海
 * 
 */
public class TrynbView extends AbstractView {

	private ResponseEntity entity;

	public TrynbView(ResponseEntity entity) {
		this.entity = entity;
	}

	public ResponseEntity getEntity() {
		return entity;
	}

	@Override
	public String getContentType() {
		return "application/json; charset=UTF-8";
	}

	@Override
	public String getBody(HttpServletRequest request, HttpServletResponse response) {
		String json = Json.toJson(entity);
		return json;
	}
}
