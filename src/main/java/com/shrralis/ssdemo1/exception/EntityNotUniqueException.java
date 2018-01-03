/*
 * The following code have been created by Yaroslav Zhyravov (shrralis).
 * The code can be used in non-commercial way for everyone.
 * But for any commercial way it needs a author's agreement.
 * Please contact the author for that:
 *  - https://t.me/Shrralis
 *  - https://twitter.com/Shrralis
 *  - shrralis@gmail.com
 *
 * Copyright (c) 2017 by shrralis (Yaroslav Zhyravov).
 */

package com.shrralis.ssdemo1.exception;

import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonError.Error;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/21/17 at 3:47 PM
 */
public class EntityNotUniqueException extends AbstractCitizenException {

	private final Entity entity;
	private final String additionalInfo;

	public EntityNotUniqueException(Entity entity) {
		super(entity.getError().getMessage());

		this.entity = entity;
		this.additionalInfo = null;
	}

	public EntityNotUniqueException(Entity entity, String additionalInfo) {
		super(entity.getError().getMessage());

		this.entity = entity;
		this.additionalInfo = additionalInfo;
	}

	public Entity getEntity() {
		return entity;
	}

	@Override
	public Error getError() {
		return entity.getError().forField(additionalInfo);
	}

	public enum Entity {
		IMAGE,
		MAP_MARKER,
		USER;

		private static final Map<Entity, JsonError.Error> entityErrorMap;

		static {
			entityErrorMap = new ConcurrentHashMap<>();

			entityErrorMap.put(IMAGE, JsonError.Error.IMAGE_ALREADY_EXISTS);
			entityErrorMap.put(MAP_MARKER, JsonError.Error.MAP_MARKER_ALREADY_EXISTS);
			entityErrorMap.put(USER, JsonError.Error.USER_ALREADY_EXISTS);
		}

		public static JsonError.Error getError(Entity entity) {
			return entityErrorMap.get(entity);
		}

		public JsonError.Error getError() {
			return entityErrorMap.get(this);
		}
	}
}
