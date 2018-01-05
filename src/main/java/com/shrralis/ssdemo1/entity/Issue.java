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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static com.shrralis.ssdemo1.entity.Issue.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
public class Issue implements Identifiable<Integer> {
    public static final String TABLE_NAME = "issues";
    public static final String ID_COLUMN_NAME = "id";
    public static final String MAP_MARKER_COLUMN_NAME = "map_marker_id";
    public static final String AUTHOR_COLUMN_NAME = "author_id";
    public static final String IMG_COLUMN_NAME = "image_id";
    public static final String TITLE_COLUMN_NAME = "title";
    public static final String TEXT_COLUMN_NAME = "text";
    public static final String TYPE_COLUMN_NAME = "type";
    public static final String CLOSED_COLUMN_NAME = "closed";
    public static final String CREATED_AT_COLUMN_NAME = "created_at";
    public static final String UPDATED_AT_COLUMN_NAME = "updated_at";
    public static final int MAX_TITLE_LENGTH = 32;
    public static final int MIN_TITLE_LENGTH = 4;
    public static final int MAX_TEXT_LENGTH = 2048;
    public static final int MIN_TEXT_LENGTH = 64;

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issues_seq_gen")
	@SequenceGenerator(name = "issues_seq_gen", sequenceName = "issues_id_seq", allocationSize = 1)
	@Column(name = ID_COLUMN_NAME, nullable = false, unique = true)
	private Integer id;

	@NotNull
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = MAP_MARKER_COLUMN_NAME, nullable = false)
	private MapMarker mapMarker;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = AUTHOR_COLUMN_NAME, nullable = false)
	private User author;

	@NotNull
	@Size(min = MIN_TITLE_LENGTH, max = MAX_TITLE_LENGTH)
	@Column(name = TITLE_COLUMN_NAME, nullable = false, length = MAX_TITLE_LENGTH)
	private String title;

	@NotNull
	@Size(min = MIN_TEXT_LENGTH, max = MIN_TEXT_LENGTH)
	@Column(name = TEXT_COLUMN_NAME, nullable = false, length = MAX_TEXT_LENGTH)
	private String text;

	@NotBlank
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = IMG_COLUMN_NAME)
	private Image image;

	@NotNull
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = TYPE_COLUMN_NAME, nullable = false)
	private Type type;

	@NotNull
	@Column(name = CLOSED_COLUMN_NAME, nullable = false)
	private Boolean closed;

	@NotNull
	@Column(name = CREATED_AT_COLUMN_NAME, nullable = false)
	private LocalDateTime createdAt;

	@NotNull
	@Column(name = UPDATED_AT_COLUMN_NAME, nullable = false)
	private LocalDateTime updatedAt;

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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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

	public Type getType() {
        return type;
    }

	public void setType(Type type) {
        this.type = type;
    }

    public boolean isClosed() {
        return closed;
    }

	public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

	public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

	public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Entity
    @Table(name = Type.TABLE_NAME)
    public static class Type implements Identifiable<Integer> {
        public static final String TABLE_NAME = "issue_types";
        public static final String ID_COLUMN_NAME = "id";
        public static final String NAME_COLUMN_NAME = "name";
        public static final int MIN_NAME_LENGTH = 4;
        public static final int MAX_NAME_LENGTH = 16;

        @Id
        @NotNull
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "issue_types_seq_gen")
        @SequenceGenerator(name = "issue_types_seq_gen", sequenceName = "issue_types_id_seq")
        @Column(name = ID_COLUMN_NAME, nullable = false, unique = true)
        private Integer id;

	    @NotNull
	    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH)
	    @Column(name = NAME_COLUMN_NAME, nullable = false, unique = true, length = MAX_NAME_LENGTH)
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
}
