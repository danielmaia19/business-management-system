package com.danielmaia.businessmanagementsystem.Model;

import javax.persistence.*;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long client_id;

    @Column
    private String name;
    private String addressLineOne;
    private String addressLineTwo;
    private String city;
    private String region;
    private String postCode;
    private String country;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

    public String getAddressLineOne() {
        return addressLineOne;
    }

    public void setAddressLineOne(String addressLineOne) {
        this.addressLineOne = addressLineOne;
    }

    public String getAddressLineTwo() {
        return addressLineTwo;
    }

    public void setAddressLineTwo(String addressLineTwo) {
        this.addressLineTwo = addressLineTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
