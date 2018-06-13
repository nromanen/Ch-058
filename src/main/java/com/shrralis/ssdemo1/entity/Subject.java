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

import static com.shrralis.ssdemo1.entity.Subject.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
@Builder
public class Subject implements Identifiable<Integer> {

	public static final String TABLE_NAME = "subjects";
	public static final String ID_COLUMN_NAME = "id";
	public static final String NAME_COLUMN_NAME = "name";

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjects_seq_gen")
	@SequenceGenerator(name = ID_COLUMN_NAME, sequenceName = "subjects_id_seq", allocationSize = 1)
	@Column(name = ID_COLUMN_NAME, nullable = false, unique = true)
	private Integer id;

	@NotNull
	@NotBlank
	@Column(name = NAME_COLUMN_NAME, nullable = false)
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
