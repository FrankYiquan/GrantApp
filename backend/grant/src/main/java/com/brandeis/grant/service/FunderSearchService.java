package com.brandeis.grant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.brandeis.grant.model.Funder;
import com.brandeis.grant.model.FunderDocument;
import com.brandeis.grant.repository.FunderRepository;
import com.brandeis.grant.repository.FunderSearchRepository;

@Service

public class FunderSearchService {
   
    private FunderRepository funderRepository;
    private FunderSearchRepository funderSearchRepository;

    public FunderSearchService(FunderRepository funderRepository, FunderSearchRepository funderSearchRepository) {
        this.funderRepository = funderRepository;
        this.funderSearchRepository = funderSearchRepository;
    }

    //sync all funders to Elasticsearch
    public void syncAllFundersToES() {
        List<Funder> funderList = funderRepository.findAll();
        List<FunderDocument> docs = funderList.stream().map(funder -> {
            FunderDocument doc = new FunderDocument();
            doc.setFunderId(funder.getFunderId());
            doc.setFunderName(funder.getFunderName());
            return doc;
        }).toList();

        funderSearchRepository.saveAll(docs);

    }

    // Exact match search for funders by name
    public List<FunderDocument> exactMatch(String funderName) {
        if (funderName == null || funderName.isEmpty()) {
            throw new IllegalArgumentException("Funder name must not be null or empty");
        }
        return funderSearchRepository.findByFunderName(funderName);
    }

    // Fuzzy search for funders by name
    public List<FunderDocument> fuzzySearch(String funderName) {
        if (funderName == null || funderName.isEmpty()) {
            throw new IllegalArgumentException("Funder name must not be null or empty");
        }
        return funderSearchRepository.fuzzySearch(funderName);
    }


        

}
