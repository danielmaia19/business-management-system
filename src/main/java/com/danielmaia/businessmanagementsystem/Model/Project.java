package com.danielmaia.businessmanagementsystem.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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
    @Column(length = 1024)
    private String description;
    private String status = "To Do";
    private int progress;
    private Date createdOn;
    private BigDecimal quotePrice;
    private String projectManager;

    @Transient
    private int timeSpent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name="client_id", referencedColumnName = "client_id")
    private Client client;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<HoursWorked> hoursWorked = new ArrayList<>();

    public Project() {
    }

    public Project(String projectName) {
        this.name = projectName;
    }

    public Project(String projectName, Client client) {
        this.name = projectName;
        this.client = client;
    }

}
