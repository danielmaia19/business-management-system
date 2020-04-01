package com.danielmaia.businessmanagementsystem.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id", unique = true)
    private Long role_id;

    @Column
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role() { }
}
