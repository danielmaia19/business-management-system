package com.danielmaia.businessmanagementsystem.Model;

import org.springframework.transaction.annotation.Transactional;
import javax.persistence.*;

@Entity
@Transactional
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int project_id;

    @Column
    private String name;

    public Project() {
    }

}
