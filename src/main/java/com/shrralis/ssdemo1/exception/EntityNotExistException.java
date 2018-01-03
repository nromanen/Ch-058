package com.shrralis.ssdemo1.exception;

import com.shrralis.tools.model.JsonError;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/2/18 at 4:50 PM
 */
public class EntityNotExistException extends AbstractCitizenException {

	private final Entity entity;
	private final String additionalInfo;

	public EntityNotExistException(Entity entity) {
		super(entity.getError().getMessage());

		this.entity = entity;
		this.additionalInfo = null;
	}

	public EntityNotExistException(Entity entity, String additionalInfo) {
		super(entity.getError().getMessage());

		this.entity = entity;
		this.additionalInfo = additionalInfo;
	}

	public Entity getEntity() {
		return entity;
	}

	@Override
	public JsonError.Error getError() {
		return entity.getError().forField(additionalInfo);
	}

	public enum Entity {
		IMAGE,
		MAP_MARKER,
		USER,
		RECOVERY_TOKEN;

		private static final Map<Entity, JsonError.Error> entityErrorMap;

		static {
			entityErrorMap = new ConcurrentHashMap<>();

			entityErrorMap.put(IMAGE, JsonError.Error.IMAGE_NOT_EXIST);
			entityErrorMap.put(MAP_MARKER, JsonError.Error.MAP_MARKER_NOT_EXIST);
			entityErrorMap.put(USER, JsonError.Error.USER_NOT_EXIST);
			entityErrorMap.put(RECOVERY_TOKEN, JsonError.Error.RECOVERY_TOKEN_EXPIRED);
		}

		public static JsonError.Error getError(Entity entity) {
			return entityErrorMap.get(entity);
		}

		public JsonError.Error getError() {
			return entityErrorMap.get(this);
		}
	}
}
