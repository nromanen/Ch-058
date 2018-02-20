package com.softserveinc.geocitizen.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Msg {

	private ObjectId _id;
	private String text;


	public Msg(ObjectId _id, String text) {
			this._id = _id;
			this.text = text;
		}

	public ObjectId getId() {
		return _id;
	}

	public void setId(ObjectId _id) {
		this._id = _id;
	}

	public String getText() {
			return text;
		}

	public void setText(String text) {
			this.text = text;
		}
}
