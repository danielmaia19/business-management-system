package com.danielmaia.businessmanagementsystem.Service;

import com.danielmaia.businessmanagementsystem.Model.HoursWorked;
import com.danielmaia.businessmanagementsystem.Model.Project;
import com.danielmaia.businessmanagementsystem.Repository.HoursWorkedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoursWorkedService {

    @Autowired
    private HoursWorkedRepository hoursWorkedRepository;

    public void saveHoursWorked(HoursWorked hoursWorked) {
        hoursWorkedRepository.save(hoursWorked);
    }

    public List<HoursWorked> findAllByProject(Project project) {
        return hoursWorkedRepository.findAllByProject(project);
    }

}
