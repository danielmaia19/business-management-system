package com.danielmaia.businessmanagementsystem.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "client_notes")
public class ClientNote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long id;

    @Column
    @NotNull
    @NotEmpty
    private String user_note;
    private LocalDate submittedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="clientId", referencedColumnName = "clientId")
    private Client client;

    public ClientNote() {
    }

    public ClientNote(String user_note, Client client) {
        this.user_note = user_note;
        this.client = client;
    }
}
