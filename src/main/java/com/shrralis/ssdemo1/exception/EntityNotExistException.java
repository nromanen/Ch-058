package com.shrralis.ssdemo1.exception;

import com.shrralis.ssdemo1.exception.system.Entity;
import com.shrralis.tools.model.JsonError;

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
}
