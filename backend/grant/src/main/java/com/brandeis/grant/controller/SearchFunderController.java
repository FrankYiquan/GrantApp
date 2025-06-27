package com.brandeis.grant.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brandeis.grant.model.FunderDocument;
import com.brandeis.grant.service.FunderSearchService;


@RestController
@RequestMapping("/api/searchfunder")
public class SearchFunderController {

    private final FunderSearchService funderSearchService;

    public SearchFunderController(FunderSearchService funderSearchService) {
        this.funderSearchService = funderSearchService;
    }
    
    
    @GetMapping("/contains")
    public ResponseEntity<List<FunderDocument>> searchFunderByName(@RequestParam("funderName")String funderName) {
        if (funderName == null || funderName.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        List<FunderDocument> funders = funderSearchService.exactMatch(funderName);
        if (funders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(funders);
    }

    @GetMapping("/fuzzy")
    public ResponseEntity<List<FunderDocument>> fuzzySearchByName(@RequestParam String funderName) {
        if (funderName == null || funderName.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        List<FunderDocument> funders = funderSearchService.fuzzySearch(funderName);
        if (funders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(funders);
    }
    

}
