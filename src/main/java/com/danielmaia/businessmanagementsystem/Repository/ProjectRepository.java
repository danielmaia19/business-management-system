package com.danielmaia.businessmanagementsystem.Repository;

import com.danielmaia.businessmanagementsystem.Model.Client;
import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {
    List<Project> findAllByClient(Client client);
    Project findByName(String name);
    void deleteProjectByName(String name);
    void deleteByProjectId(int id);
    int countProjectsByClient(Client client);
    int countProjectsByStatusEqualsOrStatusEquals(String todo, String inprogress);
    Project findByProjectId(int id);
}
