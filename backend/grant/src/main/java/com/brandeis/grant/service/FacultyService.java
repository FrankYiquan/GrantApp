package com.brandeis.grant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brandeis.grant.model.Award;
import com.brandeis.grant.model.Faculty;
import com.brandeis.grant.repository.FacultyRepository;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    // Get all faculty members
    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    // Get a faculty member by ID
    public Faculty getFacultyById(String id) {
        return facultyRepository.findById(id).orElse(null);
    }

    // Add a new faculty member
    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }


    // Delete a faculty member
    public void deleteFaculty(String id) {
        facultyRepository.deleteById(id);
    }

    // Get all awards for a specific faculty member
    public List<Award> getAwardsForFaculty(String facultyId) {
    Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
    if (faculty != null) {
        return faculty.getAwards();
    }
    return new ArrayList<>();
}
}