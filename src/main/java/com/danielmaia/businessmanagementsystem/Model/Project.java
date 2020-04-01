package com.danielmaia.businessmanagementsystem.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;

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
