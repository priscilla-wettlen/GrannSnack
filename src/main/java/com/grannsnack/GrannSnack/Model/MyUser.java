package com.grannsnack.GrannSnack.Model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "USER")
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Nonnull
    private String userName;

    @Nonnull
    private String email;

    @Nonnull
    private String password;
    @Nonnull
    private String apartmentCode;
    @Nonnull
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Nonnull
    public String getUserEmail() {
        return email;
    }

    public void setUserEmail(@Nonnull String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Nonnull
    public String getApartmentCode() {
        return apartmentCode;
    }

    public void setApartmentCode(@Nonnull String apartmentCode) {
        this.apartmentCode = apartmentCode;
    }

    @Nonnull
    public String getRole() {
        return role;
    }

    public void setRole(@Nonnull String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
