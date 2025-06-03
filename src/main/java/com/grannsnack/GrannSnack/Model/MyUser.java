package com.grannsnack.GrannSnack.Model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

/**
 * This class represents a user in the system. It does this by using the Entity annotation. This makes the class
 * work automatically with our database. Creating a table but also getting the user from the database as objects without
 * the extra work of parsing.
 * <p>
 * It does this by having annotations for every instance variable. The @Column annotation tells spring boot and jpa which
 * column the information is part of.
 * <p>
 * The @Id annotation tells spring that the value is to be treated as an Id, which is usually a primary key.
 * The @GeneratedValue annotation makes sure that the value is ever-increasing.
 * <p>
 * The @Nonnull annotation tells spring boot that this variable cannot be null. If one tries to make it null sping throws an exception.
 *
 * @Author Joel Seger
 */
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
    private String association;
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
    public String getAssociation() {
        return association;
    }

    public void setAssociation(@Nonnull String association) {
        this.association = association;
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
