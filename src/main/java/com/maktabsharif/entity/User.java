package com.maktabsharif.entity;

import com.maktabsharif.entity.enumeration.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.awt.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "family_name")
    private String familyName;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "register_date")
    private Long registerDate;

    @Column(name = "user_role")
    private UserRole userRole;

    @Column(name = "expert_status")
    private ExpertStatus expertStatus;
    @Column(name = "expert_point")
    private Integer expertPoint;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany/*(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)*/
    @JoinTable(
            name = "user_services",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "services_id", referencedColumnName = "id")})
    private List<Services> services;

    @OneToOne(cascade = CascadeType.MERGE/*, fetch = FetchType.EAGER*/)
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "user")
    private List<Order> order;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "user")
    private List<Suggestions> suggestions;

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }


    @Override
    public Long getId() {
        return id;
    }


    @Override
    public void setId(Long id) {
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

    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }
}
