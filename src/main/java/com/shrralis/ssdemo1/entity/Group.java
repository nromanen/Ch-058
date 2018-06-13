package com.shrralis.ssdemo1.entity;

import com.shrralis.ssdemo1.entity.interfaces.Identifiable;
import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.shrralis.ssdemo1.entity.Group.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
@Builder
public class Group implements Identifiable<Integer> {

	public static final String TABLE_NAME = "groups";
	public static final String NUMBER_COLUMN_NAME = "number";
	public static final String ID_COLUMN_NAME = "id";

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_seq_gen")
	@SequenceGenerator(name = ID_COLUMN_NAME, sequenceName = "groups_id_seq", allocationSize = 1)
	@Column(name = ID_COLUMN_NAME, nullable = false, unique = true)
	private Integer id;

	@NotNull
	@NotBlank
	@Column(name = NUMBER_COLUMN_NAME, nullable = false)
	private String number;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
