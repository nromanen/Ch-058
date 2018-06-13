package com.shrralis.ssdemo1.entity;

import com.shrralis.ssdemo1.entity.interfaces.Identifiable;
import lombok.Builder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.shrralis.ssdemo1.entity.Student.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
@Builder
public class Student implements Identifiable<Integer> {

	public static final String NAME_PATTERN = "^[A-ZА-ЯІЇЄ]('?[a-zа-яіїє])+?(-[A-ZА-ЯІЇЄ]('?[a-zа-яіїє])+)?$";
	public static final String TABLE_NAME = "teachers";
	public static final String ID_COLUMN_NAME = "id";
	public static final String NAME_COLUMN_NAME = "name";
	public static final String SURNAME_COLUMN_NAME = "surname";
	public static final String MIDDLENAME_COLUMN_NAME = "middleName";
	public static final String GROUP_COLUMN_NAME = "group_id";
	public static final int MAX_NAME_LENGTH = 16;
	public static final int MIN_NAME_LENGTH = 1;
	public static final int MAX_SURNAME_LENGTH = 32;
	public static final int MIN_SURNAME_LENGTH = 1;

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "students_seq_gen")
	@SequenceGenerator(name = ID_COLUMN_NAME, sequenceName = "students_id_seq", allocationSize = 1)
	@Column(name = ID_COLUMN_NAME, nullable = false, unique = true)
	private Integer id;

	@NotNull
	@NotBlank
	@Pattern(regexp = NAME_PATTERN)
	@Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH)
	@Column(name = NAME_COLUMN_NAME, nullable = false, length = MAX_NAME_LENGTH)
	private String name;

	@NotNull
	@NotBlank
	@Pattern(regexp = NAME_PATTERN)
	@Size(min = MIN_SURNAME_LENGTH, max = MAX_SURNAME_LENGTH)
	@Column(name = SURNAME_COLUMN_NAME, nullable = false, length = MAX_SURNAME_LENGTH)
	private String surname;

	@NotNull
	@NotBlank
	@Pattern(regexp = NAME_PATTERN)
	@Size(min = MIN_SURNAME_LENGTH, max = MAX_SURNAME_LENGTH)
	@Column(name = MIDDLENAME_COLUMN_NAME, nullable = false, length = MAX_SURNAME_LENGTH)
	private String middleName;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = GROUP_COLUMN_NAME, nullable = false)
	private Group group;

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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
}
