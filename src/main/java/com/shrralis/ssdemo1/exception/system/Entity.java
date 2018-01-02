package com.shrralis.ssdemo1.exception.system;

import com.google.common.collect.ImmutableMap;
import com.shrralis.tools.model.JsonError;

import java.util.Map;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/2/18 at 4:51 PM
 */
public enum Entity {
	IMAGE,
	MAP_MARKER,
	USER;

	private static final Map<Entity, JsonError.Error> entityErrorMap;

	static {
		entityErrorMap = new ImmutableMap.Builder<Entity, JsonError.Error>()
				.put(IMAGE, JsonError.Error.IMAGE_ALREADY_EXISTS)
				.put(MAP_MARKER, JsonError.Error.MAP_MARKER_ALREADY_EXISTS)
				.put(USER, JsonError.Error.USER_ALREADY_EXISTS)
				.build();
	}

	public static JsonError.Error getError(Entity entity) {
		return entityErrorMap.get(entity);
	}

	public JsonError.Error getError() {
		return entityErrorMap.get(this);
	}
}
