package com.danielmaia.businessmanagementsystem.Repository;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.ClientNote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientNoteRepository extends CrudRepository<ClientNote, Long> {
    List<ClientNote> findAllByClient(Client client);
    List<ClientNote> findAllByClientOrderBySubmittedDateDesc(Client client);
    ClientNote findNoteById(Long id);
}
