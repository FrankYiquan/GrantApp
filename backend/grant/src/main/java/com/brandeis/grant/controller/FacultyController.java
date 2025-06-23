package com.brandeis.grant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.brandeis.grant.service.FacultySearchService;
import com.brandeis.grant.service.FacultyService;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;
    @Autowired
    private FacultySearchService facultySearchService;

    //upload faculty in a csv file to db

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFacultyFile(@RequestParam("file") MultipartFile file) {
        try {
            facultyService.importFacultyFromCsv(file);
            return ResponseEntity.ok("File uploaded and faculty data saved.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    //sync faculty data in db to elasticsearch
    @PostMapping("/sync-faculty")
    public ResponseEntity<String> syncFaculty() {
        facultySearchService.syncAllFacultiesToES();
        return ResponseEntity.ok("Faculty data synced to Elasticsearch");
    }

    

}
