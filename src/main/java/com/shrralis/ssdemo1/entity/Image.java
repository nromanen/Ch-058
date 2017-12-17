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

    private Integer id;
    private ImageType type = ImageType.USER;
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
    public ImageType getType() {
        return type;
    }

    public void setType(ImageType type) {
        this.type = type;
    }

    @NotNull
    @Size(min = 5, max = 128)
    @Column(name = SRC_COLUMN_NAME, nullable = false, length = 128)
    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @NotNull
    @Size(min = 32, max = 32)
    @Column(name = HASH_COLUMN_NAME, nullable = false, length = 32)
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public enum ImageType {
        USER
    }
}
