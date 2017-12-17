package com.shrralis.ssdemo1.entity;

import com.shrralis.ssdemo1.entity.interfaces.Identifiable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.shrralis.ssdemo1.entity.User.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
public class User implements Identifiable<Integer> {
    public static final String TABLE_NAME = "users";
    public static final String ID_COLUMN_NAME = "id";
    public static final String LOGIN_COLUMN_NAME = "login";
    public static final String PASS_COLUMN_NAME = "password";
    public static final String IMAGE_COLUMN_NAME = "image_id";

    private Integer id;
    private String login;
    private String password;
    private Image image;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "users_id_seq")
    @Column(name = ID_COLUMN_NAME, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    @Size(min = 4, max = 16)
    @Column(name = LOGIN_COLUMN_NAME, nullable = false, length = 16)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @NotNull
    @Size(min = 32, max = 32)
    @Column(name = PASS_COLUMN_NAME, nullable = false, length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = IMAGE_COLUMN_NAME)
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
