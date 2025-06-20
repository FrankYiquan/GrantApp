package com.brandeis.grant.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.brandeis.grant.model.Award;
import com.brandeis.grant.model.Faculty;
import com.brandeis.grant.model.FacultyDocument;
import com.brandeis.grant.repository.FacultyRepository;
import com.brandeis.grant.repository.FacultySearchRepository;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private FacultySearchRepository facultySearchRepository;

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

    // Import faculty data from CSV file
    public void importFacultyFromCsv(MultipartFile file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        boolean firstLine = true;

        while ((line = reader.readLine()) != null) {
            if (firstLine) {
                firstLine = false; // skip header
                continue;
            }

            String[] fields = line.split(",", -1); // -1 to include empty strings

            if (fields.length >= 4) {
                Faculty faculty = new Faculty();
                faculty.setFacultyId(fields[0].trim());
                faculty.setDisplayName(fields[1].trim());
                faculty.setOrcid(fields[3].trim());

                // Handle semicolon-separated alternative names
                String altNamesRaw = fields[2].trim();
                if (!altNamesRaw.isEmpty()) {
                    List<String> alternatives = Arrays.stream(altNamesRaw.split(";"))
                                                    .map(String::trim)
                                                    .collect(Collectors.toList());
                    faculty.setDisplayNameAlternatives(alternatives);
                } else {
                    faculty.setDisplayNameAlternatives(new ArrayList<>());
                }

                facultyRepository.save(faculty);
            }
        }
    }


    public void syncAllFacultiesToES() {
        List<Faculty> facultyList = facultyRepository.findAll();
        List<FacultyDocument> docs = facultyList.stream().map(faculty -> {
            FacultyDocument doc = new FacultyDocument();
            doc.setFacultyId(faculty.getFacultyId());
            doc.setDisplayName(faculty.getDisplayName());
            doc.setOrcid(faculty.getOrcid());
            doc.setDisplayNameAlternatives(faculty.getDisplayNameAlternatives());
            return doc;
        }).toList();

        facultySearchRepository.saveAll(docs);
    }

    public List<FacultyDocument> findByDisplayNameContainingIgnoreCase(String name) {
        return facultySearchRepository.findByDisplayNameContainingIgnoreCase(name);
    }


}
