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

import static com.shrralis.ssdemo1.entity.User.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
public class User implements Identifiable<Integer> {
    public static final String TABLE_NAME = "users";
    public static final String ID_COLUMN_NAME = "id";
    public static final String LOGIN_COLUMN_NAME = "login";
    public static final String TYPE_COLUMN_NAME = "type";
    public static final String EMAIL_COLUMN_NAME = "email";
    public static final String PASS_COLUMN_NAME = "password";
    public static final String IMAGE_COLUMN_NAME = "image_id";
    public static final String NAME_COLUMN_NAME = "name";
    public static final String SURNAME_COLUMN_NAME = "surname";
    public static final int MAX_LOGIN_LENGTH = 16;
    public static final int MIN_LOGIN_LENGTH = 4;
    public static final int MAX_EMAIL_LENGTH = 256;
    public static final int MIN_EMAIL_LENGTH = 6;
    public static final int MAX_PASSWORD_LENGTH = 32;
    public static final int MIN_PASSWORD_LENGTH = MAX_PASSWORD_LENGTH;
    public static final int MAX_NAME_LENGTH = 16;
    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_SURNAME_LENGTH = 32;
    public static final int MIN_SURNAME_LENGTH = 1;

    private Integer id;
    private String login;
    private Type type = Type.USER;
    private String email;
    private String password;
    private Image image;
    private String name;
    private String surname;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "users_id_seq")
    @Column(name = ID_COLUMN_NAME, nullable = false, unique = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    @Size(min = MIN_LOGIN_LENGTH, max = MAX_LOGIN_LENGTH)
    @Column(name = LOGIN_COLUMN_NAME, nullable = false, unique = true, length = MAX_LOGIN_LENGTH)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = TYPE_COLUMN_NAME, nullable = false)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @NotNull
    @Size(min = MIN_EMAIL_LENGTH, max = MAX_EMAIL_LENGTH)
    @Column(name = EMAIL_COLUMN_NAME, nullable = false, unique = true, length = MAX_EMAIL_LENGTH)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH)
    @Column(name = PASS_COLUMN_NAME, nullable = false, length = MAX_PASSWORD_LENGTH)
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

    @NotNull
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH)
    @Column(name = NAME_COLUMN_NAME, nullable = false, length = MAX_NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Size(min = MIN_SURNAME_LENGTH, max = MAX_SURNAME_LENGTH)
    @Column(name = SURNAME_COLUMN_NAME, nullable = false, length = MAX_SURNAME_LENGTH)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public enum Type {
        BANNED,
        USER,
        ADMIN,
        MASTER
    }

    public static final class Builder {
        private User user;

        private Builder() {
            user = new User();
        }

        public static Builder anUser() {
            return new Builder();
        }

        public Builder setId(Integer id) {
            user.setId(id);
            return this;
        }

        public Builder setLogin(String login) {
            user.setLogin(login);
            return this;
        }

        public Builder setType(Type type) {
            user.setType(type);
            return this;
        }

        public Builder setEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder setPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder setImage(Image image) {
            user.setImage(image);
            return this;
        }

        public Builder setName(String name) {
            user.setName(name);
            return this;
        }

        public Builder setSurname(String surname) {
            user.setSurname(surname);
            return this;
        }

        public User build() {
            return user;
        }
    }
}
