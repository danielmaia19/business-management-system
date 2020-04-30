package com.danielmaia.businessmanagementsystem.Repository;

import com.danielmaia.businessmanagementsystem.Model.HoursWorked;
import com.danielmaia.businessmanagementsystem.Model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HoursWorkedRepository extends CrudRepository<HoursWorked, Long> {

    List<HoursWorked> findAllByProject(Project project);

}
