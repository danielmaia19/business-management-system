package com.danielmaia.businessmanagementsystem.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Transactional
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private int project_id;

    @Column
    private String name;
    private String contactPerson;
    private String description;
    private String status = "To do";
    private int progress;
    private Date created_on;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name="client_id", referencedColumnName = "client_id")
    private Client client;

    public Project() {
    }

    public Project(String projectName) {
        this.name = projectName;
    }

    public Project(String projectName, User user) {
        this.name = projectName;
        this.user = user;
    }

}
