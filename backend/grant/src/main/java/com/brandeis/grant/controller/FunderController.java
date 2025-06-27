package com.brandeis.grant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.brandeis.grant.service.FunderSearchService;
import com.brandeis.grant.service.FunderService;


@RestController
@RequestMapping("/api/funder")
public class FunderController {
    @Autowired
    private FunderService funderService;
    @Autowired
    private FunderSearchService funderSearchService ;

    // Endpoint to upload funder data from a CSV file
     @PostMapping("upload")
    public ResponseEntity<?> importFunderfromCSV(@RequestParam("file") MultipartFile filePath){
        try {
            funderService.importFundersFromCSV(filePath);
            return ResponseEntity.ok("Funder data imported successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error importing funder data: " + e.getMessage());
        }
    }


    // Endpoint to sync all funders to Elasticsearch
    @PostMapping("/sync-funder")
    public ResponseEntity<?> syncFunder() {
        funderSearchService.syncAllFundersToES();
        return ResponseEntity.status(HttpStatus.OK).body("Funder data synced to Elasticsearch successfully.");

    }

}
