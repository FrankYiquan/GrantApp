package com.brandeis.grant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.brandeis.grant.model.Award;
import com.brandeis.grant.model.Funder;
import com.brandeis.grant.repository.FunderRepository;

@Service
public class FunderService {

    private FunderRepository funderRepository;
    
    public Funder addFunder(Funder funder) {
        return funderRepository.save(funder);
    }

    public Funder getFunderById(String funderId) {
        return funderRepository.findById(funderId)
            .orElseThrow(() -> new RuntimeException("Funder not found with id " + funderId));
    }

     public List<Award> getAwardsByFunderId(String funderId) {
        Funder funder = funderRepository.findById(funderId)
            .orElseThrow(() -> new RuntimeException("Funder not found with id " + funderId));
        return funder.getAwards();
    }

}
