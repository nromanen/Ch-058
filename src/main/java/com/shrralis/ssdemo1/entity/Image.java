package com.shrralis.ssdemo1.entity;

import com.shrralis.ssdemo1.entity.interfaces.Identifiable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.shrralis.ssdemo1.entity.Image.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
public class Image implements Identifiable<Integer> {
    public static final String TABLE_NAME = "images";
    public static final String TYPE_COLUMN_NAME = "type";
    public static final String ID_COLUMN_NAME = "id";
    public static final String SRC_COLUMN_NAME = "src";
    public static final String HASH_COLUMN_NAME = "hash";
    public static final int MAX_SRC_LENGTH = 128;
    public static final int MIN_SRC_LENGTH = 4;
    public static final int MAX_HASH_LENGTH = 32;
    public static final int MIN_HASH_LENGTH = MAX_HASH_LENGTH;

    private Integer id;
    private Type type = Type.ISSUE;
    private String src;
    private String hash;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "images_seq_gen")
    @SequenceGenerator(name = "images_seq_gen", sequenceName = "images_id_seq")
    @Column(name = ID_COLUMN_NAME, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = TYPE_COLUMN_NAME, nullable = false)
    public Image.Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @NotNull
    @Size(min = MIN_SRC_LENGTH, max = MAX_SRC_LENGTH)
    @Column(name = SRC_COLUMN_NAME, nullable = false, length = MAX_SRC_LENGTH)
    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @NotNull
    @Size(min = MIN_HASH_LENGTH, max = MAX_HASH_LENGTH)
    @Column(name = HASH_COLUMN_NAME, nullable = false, length = MAX_HASH_LENGTH)
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public enum Type {
        USER,
        ISSUE
    }
}
