package com.shrralis.ssdemo1.entity;

import com.shrralis.ssdemo1.entity.interfaces.Identifiable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

public class UserProvider implements Identifiable<Integer> {
    public static final String TABLE_NAME = "user_provider";
    public static final String ID_COLUMN_NAME = "id";
    public static final String PROVIDER_COLUMN_NAME = "provider";
    public static final String PROVIDER_USER_ID_COLUMN_NAME = "provider_uid";
    public static final String USER_ID_COLUMN_NAME = "user_id";

    @Id
    @NotNull
    @Column(name = ID_COLUMN_NAME, nullable = false, unique = true)
    private int id;

    @NotNull
    @Column(name = PROVIDER_COLUMN_NAME)
    private String provider;

    @NotNull
    @Column(name = PROVIDER_USER_ID_COLUMN_NAME)
    private String providerUserId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = USER_ID_COLUMN_NAME)
    private User user;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
