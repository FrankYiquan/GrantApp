package com.brandeis.grant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brandeis.grant.model.Faculty;
import com.brandeis.grant.model.FacultyDocument;
import com.brandeis.grant.repository.FacultyRepository;
import com.brandeis.grant.repository.FacultySearchRepository;

@Service
public class FacultySearchService {
     @Autowired
    private FacultySearchRepository facultySearchRepository;
    @Autowired
    private FacultyRepository facultyRepository;


    // search faculty containing the name (NOT EXACT MATCH)
    public List<FacultyDocument> findByDisplayNameContainingIgnoreCase(String name) {
        return facultySearchRepository.findByDisplayNameContainingIgnoreCase(name);
    }

    //manually sync all faculty data to Elasticsearch
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

    // fuzzy search faculty by name and displayNameAlternatives
    public List<FacultyDocument> fuzzySearchByName(String name) {
        return facultySearchRepository.fuzzySearch(name);
    }


}
