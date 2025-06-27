package com.brandeis.grant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brandeis.grant.model.FacultyDocument;
import com.brandeis.grant.repository.FacultySearchRepository;

@RestController
@RequestMapping("/api/searchfaculty")
public class SearchFacultyController {


    @Autowired
    private FacultySearchRepository facultySearchRepository;

    @GetMapping("/contains")
    public ResponseEntity<List<FacultyDocument>> searchFaculty(@RequestParam("name") String name) {
        List<FacultyDocument> result = facultySearchRepository.findByDisplayNameContainingIgnoreCase(name);
        return ResponseEntity.ok(result); 
    }

    @GetMapping("/fuzzy")
    public ResponseEntity<?> fuzzySearchFaculty(@RequestParam("name") String name) {
        try {
            List<FacultyDocument> result = facultySearchRepository.fuzzySearch(name);
            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No faculty found.");
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("An error occurred: " + e.getMessage());
        }
    }







    
}
