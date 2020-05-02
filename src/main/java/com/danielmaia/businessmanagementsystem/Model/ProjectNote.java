package com.danielmaia.businessmanagementsystem.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "project_notes")
public class ProjectNote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;

    @Column
    @NotNull
    @NotEmpty
    private String project_note;
    private LocalDate submittedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="project_id", referencedColumnName = "project_id")
    private Project project;

    public ProjectNote() {
    }

    public ProjectNote(String project_note, Project project) {
        this.project_note = project_note;
        this.project = project;
    }

}
