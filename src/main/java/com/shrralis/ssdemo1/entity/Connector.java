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
import javax.validation.constraints.NotNull;

import static com.shrralis.ssdemo1.entity.Connector.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
@Builder
public class Connector implements Identifiable<Integer> {

	public static final String TABLE_NAME = "students_groups_subjects";
	public static final String ID_COLUMN_NAME = "id";
	public static final String TEACHER_COLUMN_NAME = "teacher_id";
	public static final String SUBJECT_COLUMN_NAME = "subject_id";
	public static final String GROUP_COLUMN_NAME = "group_id";

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "students_seq_gen")
	@SequenceGenerator(name = ID_COLUMN_NAME, sequenceName = "students_id_seq", allocationSize = 1)
	@Column(name = ID_COLUMN_NAME, nullable = false, unique = true)
	private Integer id;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = GROUP_COLUMN_NAME, nullable = false)
	private Group group;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = TEACHER_COLUMN_NAME, nullable = false)
	private Teacher teacher;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = SUBJECT_COLUMN_NAME, nullable = false)
	private Subject subject;


	public void setId(Integer id) {
		this.id = id;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Override
	public Integer getId() {
		return id;
	}
}
