package com.maktabsharif.entity;

import com.maktabsharif.entity.enumeration.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @Column(name = "Id", unique = true, nullable = false)
    String id;
    @Column(name = "name")
    String name;
    @Column(name = "family_name")
    String familyName;
    @Column(name = "username", unique = true, nullable = false)
    String username;
    @Column(name = "password", nullable = false)
    String password;
    @Column(name = "email", unique = true, nullable = false)
    String email;
    @Column(name = "register_date")
    Long registerDate;

    Wallet wallet;
    @Column(name = "user_role")
    UserRole userRole;


    @Column(name = "expert_status")
    ExpertStatus expertStatus;
    @Column(name = "expert_point")
    Integer expertPoint;

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Long registerDate) {
        this.registerDate = registerDate;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public ExpertStatus getExpertStatus() {
        return expertStatus;
    }

    public void setExpertStatus(ExpertStatus expertStatus) {
        this.expertStatus = expertStatus;
    }

    public Integer getExpertPoint() {
        return expertPoint;
    }

    public void setExpertPoint(Integer expertPoint) {
        this.expertPoint = expertPoint;
    }

}
