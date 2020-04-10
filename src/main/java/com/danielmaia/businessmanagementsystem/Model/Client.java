package com.danielmaia.businessmanagementsystem.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Getter
@Setter
@Transactional
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long client_id;

    @Column
    @NotEmpty(message = "Please enter a Client name")
    private String name;

    @Column
    @NotEmpty
    private String addressLineOne;

    private String addressLineTwo;

    @NotEmpty
    private String city;

    @NotEmpty
    private String region;

    @NotEmpty
    private String postCode;

    @NotEmpty
    private String country;

    @NotEmpty
    private String description;

    @NotEmpty
    private String contactPerson;

    @NotEmpty
    private String contactPersonEmail;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Project> projects;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ClientFile> clientFiles;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ClientNote> clientNotes;

    public Client(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public Client() {}

    public Client(String name) {
        this.name = name;
    }
}


