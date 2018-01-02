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

package com.shrralis.ssdemo1.entity;

import com.shrralis.ssdemo1.entity.interfaces.Identifiable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.shrralis.ssdemo1.entity.Issue.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
public class Issue implements Identifiable<Integer> {
    public static final String TABLE_NAME = "issues";
    public static final String ID_COLUMN_NAME = "id";
    public static final String MAP_MARKER_COLUMN_NAME = "map_marker_id";
    public static final String AUTHOR_COLUMN_NAME = "author_id";
    public static final String TITLE_COLUMN_NAME = "title";
    public static final String TEXT_COLUMN_NAME = "text";
    public static final int MAX_TITLE_LENGTH = 32;
    public static final int MIN_TITLE_LENGTH = 4;
    public static final int MAX_TEXT_LENGTH = 2048;
    public static final int MIN_TEXT_LENGTH = 64;

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issues_seq_gen")
	@SequenceGenerator(name = "issues_seq_gen", sequenceName = "issues_id_seq")
	@Column(name = ID_COLUMN_NAME, nullable = false, unique = true)
	private Integer id;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = MAP_MARKER_COLUMN_NAME)
	private MapMarker mapMarker;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = AUTHOR_COLUMN_NAME)
	private User author;

	@NotNull
	@Size(min = MIN_TITLE_LENGTH, max = MAX_TITLE_LENGTH)
	@Column(name = TITLE_COLUMN_NAME, nullable = false, length = MAX_TITLE_LENGTH)
	private String title;

	@NotNull
	@Size(min = MIN_TEXT_LENGTH, max = MIN_TEXT_LENGTH)
	@Column(name = TEXT_COLUMN_NAME, nullable = false, length = MAX_TEXT_LENGTH)
	private String text;

	@Override
	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MapMarker getMapMarker() {
        return mapMarker;
    }

    public void setMapMarker(MapMarker mapMarker) {
        this.mapMarker = mapMarker;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Entity
    @Table(name = Type.TABLE_NAME)
    public static class Type implements Identifiable<Integer> {
        public static final String TABLE_NAME = "issue_types";
        public static final String ID_COLUMN_NAME = "id";
        public static final String NAME_COLUMN_NAME = "name";
        public static final int MIN_NAME_LENGTH = 4;
        public static final int MAX_NAME_LENGTH = 16;

        private Integer id;
        private String name;

        @Id
        @NotNull
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_types_seq_gen")
        @SequenceGenerator(name = "issue_types_seq_gen", sequenceName = "issue_types_id_seq")
        @Column(name = ID_COLUMN_NAME, nullable = false, unique = true)
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @NotNull
        @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH)
        @Column(name = NAME_COLUMN_NAME, nullable = false, unique = true, length = MAX_NAME_LENGTH)
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
